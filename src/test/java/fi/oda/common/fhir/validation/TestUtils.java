package fi.oda.common.fhir.validation;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;
import org.hl7.fhir.dstu3.model.*;

import ca.uhn.fhir.context.FhirContext;
public class TestUtils {
    public static Questionnaire readQuestionnaire(String resourceFile, FhirContext fhirContext) {
        return readFhirResourceFromResources(Questionnaire.class, resourceFile, fhirContext);
    }

    public static QuestionnaireResponse readQuestionnaireResponse(String resourceFile, Questionnaire questionnaire,
            FhirContext fhirContext) {
        QuestionnaireResponse questionnaireResponse = readFhirResourceFromResources(QuestionnaireResponse.class,
                resourceFile, fhirContext);
        questionnaireResponse.setQuestionnaire(createLocalReference(questionnaire));
        return questionnaireResponse;
    }

    public static Reference createLocalReference(BaseResource referenceTarget) {
        return new Reference(new IdType(referenceTarget.fhirType(), referenceTarget.getIdElement().getIdPart()));
    }

    public static <T extends Resource> T readFhirResourceFromResources(Class<T> fhirResourceType, String resourcePath,
            FhirContext fhirContext) {
        return fhirContext.newJsonParser().parseResource(fhirResourceType, readResource(resourcePath));
    }

    public static String readResource(String resourcePath) {
        try {
            return IOUtils.toString(TestUtils.class.getClassLoader().getResourceAsStream(resourcePath),
                    StandardCharsets.UTF_8);
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
    }
}
