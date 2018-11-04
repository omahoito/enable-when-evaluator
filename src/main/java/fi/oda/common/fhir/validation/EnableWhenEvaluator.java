package fi.oda.common.fhir.validation;

import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
public interface EnableWhenEvaluator {
    /**
     * Evaluates whether a QuestionnaireItemComponent is enabled or not
     */   
    public boolean isEnabled(QuestionnaireItemComponent questionnareItem, Element questionnaireResponse);
}
