package fi.oda.common.fhir.validation.odaquestionnaire;

import static fi.oda.common.fhir.validation.utils.TestUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.*;
import java.util.stream.*;

import org.hl7.fhir.dstu3.model.*;
import org.junit.Test;

import fi.oda.common.fhir.validation.FHIRPathTestBase;

public class HenkilokohtainenApuQuestionnaireTest extends FHIRPathTestBase {
    private final String QUESTINNAIRE_FOLDER = "palveluarvio";
    private final String QUESTINNAIRERESPONSE_FOLDER = "palveluarvio";

    private final Collection<String> expectedHidden = 
            Stream.of(
                    "Q3_JOHTUUKO_TARPEESI_IKAANTYMISEEN_LIITTYVISTA_SAIRAUKSISTA_JA_RAJOITTEISTA",
                    "Q4_JOHTUUKO_TARPEESI_PITKAAIKAISESTA_TAI_ETENEVASTA_SAIRAUDESTA_TAI_VAMMASTA",
                    "Q5_OSAATKO_JA_PYSTYTKO_ITSE_MAARITTELEMAAN_TARVITSEMASI_AVUN_SISALLON_JA_TOTEUTTAMISTAVAN")
            .collect(Collectors.toSet());

    @Test
    public void henkilokohtainenApuAllHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(
                QUESTINNAIRE_FOLDER + "/Questionnaire-henkilokohtainen-apu.json",
                fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/QuestionnaireResponse-henkilokohtainen-apu-hidden.json", questionnaire,
                fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void henkilokohtainenApuVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/Questionnaire-omaishoidon-tuki.json",
                fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/QuestionnaireResponse-omaishoidon-tuki-visible.json", questionnaire,
                fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(Collections.emptySet()));
    }

}
