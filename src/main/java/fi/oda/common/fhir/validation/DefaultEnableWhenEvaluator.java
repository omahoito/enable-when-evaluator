package fi.oda.common.fhir.validation;

import static fi.oda.common.fhir.validation.QuestionnaireUtils.*;
import java.util.*;
import java.util.stream.Collectors;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Questionnaire.*;
/**
 * Evaluates Questionnaire.item.enableWhen against a QuestionnaireResponse.
 * Ignores possible modifierExtensions and extensions.
 *
 */
public class DefaultEnableWhenEvaluator implements EnableWhenEvaluator {

    @Override
    public boolean isEnabled(QuestionnaireItemComponent questionnaireItem,
            Element questionnaireResponse) {
        if (!questionnaireItem.hasEnableWhen()) {
            return true;
        }

        List<EnableWhenResult> evaluationResults = questionnaireItem.getEnableWhen()
                .stream()
                .map(enableCondition -> evaluateCondition(enableCondition, questionnaireResponse,
                        questionnaireItem.getLinkId()))
                .collect(Collectors.toList());
        return checkConditionEvaluationResults(evaluationResults, questionnaireItem);
    }
   
    
    private boolean checkConditionEvaluationResults(List<EnableWhenResult> evaluationResults,
            QuestionnaireItemComponent questionnaireItem) {
        return evaluationResults.stream().anyMatch(EnableWhenResult::isEnabled);
    }


    public EnableWhenResult evaluateCondition(QuestionnaireItemEnableWhenComponent enableCondition,
            Element questionnaireResponse, String linkId) {
        //TODO: Fix EnableWhenResult stuff
        List<Element> answerItems = findQuestionAnswers(questionnaireResponse,
                enableCondition.getQuestion());   
        if (enableCondition.hasAnswer()) {
            boolean result = answerItems.stream().anyMatch(answer -> evaluateAnswer(answer, enableCondition.getAnswer()));
            
            return new EnableWhenResult(result, linkId, null, null);

        }
        return new EnableWhenResult(false, linkId, null, null);        
    }

    private boolean evaluateAnswer(Element answer, Type expectedAnswer) {
        org.hl7.fhir.r4.model.Type actualAnswer;
        try {
            actualAnswer = answer.asType();
        } catch (FHIRException e) {
            throw new RuntimeException(e);
        }
        if (!actualAnswer.getClass().isAssignableFrom(expectedAnswer.getClass())) {
            throw new RuntimeException("Expected answer and actual answer have incompatible types");
        }
        if (expectedAnswer instanceof Coding) {
            return validateCodingAnswer((Coding)expectedAnswer, (Coding)actualAnswer);
        } else if (expectedAnswer instanceof PrimitiveType) {           
            return actualAnswer.equalsShallow(expectedAnswer);
        }
        // TODO: Quantity, Attachment, reference?
        throw new RuntimeException("Unimplemented answer type: " + expectedAnswer.getClass());
    }

    private List<Element> findQuestionAnswers(
            Element questionnaireResponse, String question) {
        List<Element> matchingItems = questionnaireResponse.getChildren("item")
                .stream()
                .flatMap(i -> findSubItems(i).stream())
                .filter(i -> hasLinkId(i, question))
                .collect(Collectors.toList());        
        return matchingItems.stream().flatMap(e-> extractAnswer(e).stream()).collect(Collectors.toList());
        
    }
    
    private List<Element> extractAnswer(Element item) {
        return item.getChildrenByName("answer").stream().flatMap(c -> c.getChildren().stream()).collect(Collectors.toList());
    }

    private boolean validateCodingAnswer(Coding expectedAnswer, Coding actualAnswer) {
        return compareSystems(expectedAnswer, actualAnswer) && compareCodes(expectedAnswer, actualAnswer);
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
