package fi.oda.common.fhir.validation;

import org.hl7.fhir.dstu3.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.dstu3.model.QuestionnaireResponse;

public interface EnableWhenEvaluator {
    /**
     * Evaluates whether a QuestionnaireItemComponent is enabled or not
     */
    public boolean isEnabled(QuestionnaireItemComponent questionnaireItem, QuestionnaireResponse questionnaireResponse);
}
