package fi.oda.common.fhir.validation;

import java.util.*;

import ca.uhn.fhir.context.FhirContext;

public class DefaultEnableWhenEvaluatorTest {
    
    private final String QUESTINNAIREFOLDER = "questionnaires";
    private final String QUESTINNAIRERESPONSEFOLDER = "questionnaireresponses";

    private final FhirContext fhirContext = FhirContext.forDstu3();
    private final DefaultEnableWhenEvaluator enableWhenEvaluator = new DefaultEnableWhenEvaluator();
    private final QuestionnaireEnableWhenEvaluator questionnaireEvaluator = new QuestionnaireEnableWhenEvaluator(
            enableWhenEvaluator);
    private final Collection<String> expectedHidden = Collections.singleton("Q_CONDITIONAL");
}
