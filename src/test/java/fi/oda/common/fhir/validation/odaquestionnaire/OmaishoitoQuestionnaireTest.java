package fi.oda.common.fhir.validation.odaquestionnaire;

import static fi.oda.common.fhir.validation.utils.TestUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.*;
import java.util.stream.*;

import org.hl7.fhir.dstu3.model.*;
import org.junit.Test;

import fi.oda.common.fhir.validation.FHIRPathTestBase;

public class OmaishoitoQuestionnaireTest extends FHIRPathTestBase {
    private final String QUESTINNAIRE_FOLDER = "palveluarvio";
    private final String QUESTINNAIRERESPONSE_FOLDER = "palveluarvio";

    private final Collection<String> expectedHidden = 
            Stream.of("Q2_TARVITSETKO_PAIVITTAISTA")
            .collect(Collectors.toSet());

    @Test
    public void omaishoitoAllHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/Questionnaire-omaishoidon-tuki.json",
                fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/QuestionnaireResponse-omaishoidon-tuki-hidden.json", questionnaire,
                fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void omaishoitoVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/Questionnaire-omaishoidon-tuki.json",
                fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/QuestionnaireResponse-omaishoidon-tuki-visible.json", questionnaire,
                fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(Collections.emptySet()));
    }

}
