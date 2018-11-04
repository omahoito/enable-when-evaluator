package fi.oda.common.fhir.validation;


import org.hl7.fhir.r4.context.IWorkerContext;
import org.hl7.fhir.r4.elementmodel.JsonParser;
import org.hl7.fhir.r4.hapi.ctx.*;

import ca.uhn.fhir.context.FhirContext;

public class TestBase {
    protected final FhirContext fhirContext = FhirContext.forR4();
    protected final IValidationSupport validationSupport = new DefaultProfileValidationSupport();
    protected final IWorkerContext workerContext = new HapiWorkerContext(fhirContext, validationSupport);
    protected final JsonParser parser = new JsonParser(workerContext);
    
}
