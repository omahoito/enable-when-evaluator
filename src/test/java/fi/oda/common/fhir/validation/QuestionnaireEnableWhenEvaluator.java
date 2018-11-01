package fi.oda.common.fhir.validation;

import static fi.oda.common.fhir.validation.QuestionnaireUtils.findSubItems;

import java.util.Set;
import java.util.stream.Collectors;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Questionnaire.QuestionnaireItemComponent;

public class QuestionnaireEnableWhenEvaluator {
    private EnableWhenEvaluator enableWhenEvaluator;

    public QuestionnaireEnableWhenEvaluator(EnableWhenEvaluator enableWhenEvaluator) {
        this.enableWhenEvaluator = enableWhenEvaluator;
    }

    public Set<String> findDisabledItems(QuestionnaireResponse questionnaireResponse, Questionnaire questionnaire) {
        return questionnaire.getItem().stream()
                .flatMap(item -> findSubItems(item, QuestionnaireUtils::getQuestionnaireItemChildren).stream())
                .filter(subItem -> !enableWhenEvaluator.isEnabled(subItem, questionnaireResponse))
                .map(QuestionnaireItemComponent::getLinkId).collect(Collectors.toSet());
    }

}
