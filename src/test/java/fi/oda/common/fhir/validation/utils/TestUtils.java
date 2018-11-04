package fi.oda.common.fhir.validation.utils;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.*;
import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.elementmodel.JsonParser;
import org.hl7.fhir.r4.elementmodel.ParserBase.ValidationPolicy;
import org.hl7.fhir.r4.model.Questionnaire;

import ca.uhn.fhir.context.FhirContext;
import fi.oda.common.fhir.validation.FHIRPathEnableWhenEvaluatorTest;
public class TestUtils {
    public static Element readQuestionnaireResponseElement(String pathToResource, FhirContext fhirContext, JsonParser parser){        
        try (InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(pathToResource)){
            return parser.parse(inputStream);
        } catch (FHIRFormatError | FHIRException | IOException e1) {
            throw new RuntimeException(e1);
        }
    }
    
    public static Questionnaire readQuestionnaire(String resourceFile, FhirContext fhirContext) {
        return readFhirResourceFromResources(Questionnaire.class, resourceFile, fhirContext);
    }

    public static <T extends org.hl7.fhir.r4.model.Resource> T readFhirResourceFromResources(Class<T> fhirResourceType, String resourcePath,
            FhirContext fhirContext) {
        return fhirContext.newJsonParser().parseResource(fhirResourceType, readResource(resourcePath));
    }
    public static String readResource(String resourcePath) {
        try (InputStream inputStream = TestUtils.class.getClassLoader().getResourceAsStream(resourcePath)){
          
            return IOUtils.toString(inputStream,
                    StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}