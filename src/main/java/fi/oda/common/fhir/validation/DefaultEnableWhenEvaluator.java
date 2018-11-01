package fi.oda.common.fhir.validation;

import static fi.oda.common.fhir.validation.QuestionnaireUtils.findSubItems;

import java.util.*;
import java.util.stream.Collectors;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Questionnaire.*;
import org.hl7.fhir.dstu3.model.QuestionnaireResponse.*;
/**
 * Evaluates Questionnaire.item.enableWhen against a QuestionnaireResponse.
 * Ignores possible modifierExtensions and extensions.
 *
 */
public class DefaultEnableWhenEvaluator implements EnableWhenEvaluator {
    @Override
    public boolean isEnabled(QuestionnaireItemComponent questionnaireItem,
            QuestionnaireResponse questionnaireResponse) {
        if (!questionnaireItem.hasEnableWhen()) {
            return true;
        }
        List<EnableWhenResult> evaluationResults = questionnaireItem.getEnableWhen().stream()
                .map(enableCondition -> evaluateCondition(enableCondition, questionnaireResponse,
                        questionnaireItem.getLinkId()))
                .collect(Collectors.toList());
        return evaluateEnableWhenResults(evaluationResults);
    }

    public boolean evaluateEnableWhenResults(List<EnableWhenResult> evaluationResults) {
        return evaluationResults.stream().anyMatch(EnableWhenResult::isEnabled);
    }

    private Optional<QuestionnaireResponseItemComponent> findTargetQuestionAnswer(
            QuestionnaireResponse questionnaireResponse, String question) {
        return questionnaireResponse.getItem().stream()
                .flatMap(item -> findSubItems(item, QuestionnaireUtils::getQuestionnaireResponseItemChildren).stream())
                .filter(item -> item.getLinkId().equals(question)).findFirst();
    }

    private EnableWhenResult evaluateCondition(QuestionnaireItemEnableWhenComponent enableCondition,
            QuestionnaireResponse questionnaireResponse, String linkId) {
        //Find the answer from the QuestionnaireResponse 
        QuestionnaireResponseItemComponent targetItem = findTargetQuestionAnswer(questionnaireResponse,
                enableCondition.getQuestion()).orElseThrow(
                        () -> new RuntimeException("EnableWhen condition refers to a non-existing Questionnaire.item"));
        
        List<Extension> modifierExtensions = new ArrayList<>();
        if (enableCondition.hasModifierExtension()){
            modifierExtensions = enableCondition.getModifierExtension();
        }
        if (enableCondition.hasHasAnswer() && enableCondition.hasAnswer()) {
            throw new RuntimeException("Expected: hasAnswer or answer, but not both. Found both.");
        }
        if (enableCondition.hasHasAnswer()) {
            if (enableCondition.getHasAnswer() == targetItem.hasAnswer()) {
                return new EnableWhenResult(true, linkId, modifierExtensions);
            }
            return new EnableWhenResult(false, linkId, modifierExtensions);
        }
        if (enableCondition.hasAnswer()) {

            boolean evaluationResult = targetItem.getAnswer().stream()
                    .anyMatch(answer -> evaluateAnswer(enableCondition.getAnswer(), answer, linkId));
            return new EnableWhenResult(evaluationResult, linkId, modifierExtensions);

        }
        return new EnableWhenResult(false, linkId, modifierExtensions);
    }

    private boolean evaluateAnswer(Type expectedAnswer, QuestionnaireResponseItemAnswerComponent actualAnswer,
            String questionLinkId) {
        if (expectedAnswer instanceof Coding) {
            validateCodingAnswer((Coding) expectedAnswer, actualAnswer, questionLinkId);
        } else if (expectedAnswer instanceof PrimitiveType) {
            if (!actualAnswer.getValue().getClass().isAssignableFrom(expectedAnswer.getClass())) {
                throw new RuntimeException("Expected answer and actual answer have incompatible types");
            }
            return actualAnswer.getValue().equalsShallow(expectedAnswer);
        }
        // TODO: Quantity, Attachment, reference?
        throw new RuntimeException("Unimplemented answer type: " + expectedAnswer.getClass());
    }

    private boolean validateCodingAnswer(Coding expectedAnswer, QuestionnaireResponseItemAnswerComponent actualAnswer,
            String questionLinkId) {
        Coding expectedCoding = (Coding) expectedAnswer;
        if (!(actualAnswer.getValue() instanceof Coding)) {
            throw new RuntimeException("Could not evaluate enableWhen. Expected Coding for answer");
        }
        return compareCodings(expectedCoding, (Coding) actualAnswer.getValue());
    }

    private boolean compareCodings(Coding expectedCoding, Coding value) {
        return compareSystems(expectedCoding, value) && compareCodes(expectedCoding, value);
    }

    private boolean compareCodes(Coding expectedCoding, Coding value) {
        if (expectedCoding.hasCode() != value.hasCode()) {
            return false;
        }
        if (expectedCoding.hasCode()) {
            return expectedCoding.getCode().equals(value.getCode());
        }
        return true;
    }

    private boolean compareSystems(Coding expectedCoding, Coding value) {
        if (expectedCoding.hasSystem() != value.hasSystem()) {
            return false;
        }
        if (expectedCoding.hasSystem()) {
            return expectedCoding.getSystem().equals(value.getSystem());
        }
        return true;
    }

}
