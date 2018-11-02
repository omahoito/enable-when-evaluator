package fi.oda.common.fhir.validation;

import org.hl7.fhir.dstu3.context.IWorkerContext;
import org.hl7.fhir.dstu3.hapi.ctx.*;
import org.hl7.fhir.dstu3.hapi.validation.DefaultProfileValidationSupport;
import org.hl7.fhir.dstu3.utils.FHIRPathEngine;

import ca.uhn.fhir.context.FhirContext;
import fi.oda.common.fhir.validation.utils.QuestionnaireEnableWhenEvaluator;

public class FHIRPathTestBase {
    protected final FhirContext fhirContext = FhirContext.forDstu3();
    protected final IValidationSupport validationSupport = new DefaultProfileValidationSupport();
    protected final IWorkerContext workerContext = new HapiWorkerContext(fhirContext, validationSupport);
    protected final FHIRPathEngine fhirPathEngine = new FHIRPathEngine(workerContext);
    protected final EnableWhenEvaluator enableWhenEvaluator = new FHIRPathEnableWhenEvaluator(fhirPathEngine);
    protected final QuestionnaireEnableWhenEvaluator questionnaireEvaluator = new QuestionnaireEnableWhenEvaluator(
            enableWhenEvaluator);
}
