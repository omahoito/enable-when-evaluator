package fi.oda.common.fhir.validation.odaquestionnaire;

import static fi.oda.common.fhir.validation.utils.TestUtils.*;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.*;
import java.util.stream.*;

import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.utils.FHIRPathEngine;
import org.junit.Test;

import fi.oda.common.fhir.validation.*;
import fi.oda.common.fhir.validation.utils.QuestionnaireEnableWhenEvaluator;

public class KuljetusapuQuestionnaireTest extends TestBase {
    private final String QUESTINNAIRE_FOLDER = "palveluarvio";
    private final String QUESTINNAIRERESPONSE_FOLDER = "palveluarvio";
    private final FHIRPathEngine fhirPathEngine = new FHIRPathEngine(workerContext);
    private final EnableWhenEvaluator enableWhenEvaluator = new FHIRPathEnableWhenEvaluator(fhirPathEngine);
    private final QuestionnaireEnableWhenEvaluator questionnaireEvaluator = new QuestionnaireEnableWhenEvaluator(
            enableWhenEvaluator);
    private final Collection<String> expectedHidden = 
            Stream.of(
                "Q2_ONKO_KAYTOSSA_LIIKKUMISEN_APUVALINEITA",
                "Q3_TARVITSETKO_APUA_TERVEYDENHUOLTOMATKOIHIN", 
                "Q4_1_TARVITSETKO_APUA_TYO_TAI_OPISKELUMATKOIHIN",
                "Q4_2_TARVITSETKO_APUA_ASIOINTI_JA_VIRKISTYSMATKOIHIN",
                "Q5_PYSTYTKO_LIIKKUMAAN_ITSENAISESTI_JOUKKOLIIKENTEELLA",
                "Q6_PYSTYTKO_LIIKKUMAAN_JOUKKO_PALVELU_LIIKENTEELLA_SAATTAJAN_AVULLA",
                "Q7_JOHTUUKO_TARPEESI_IKAANTYMISESTA",
                "Q9_JOHTUUKO_TARPEESI_PITKAAIKAISESTA_TAI_ETENEVASTA_SAIRAUDESTA_TAI_VAMMASTA",
                "Q10_1_ASUTKO_AVO_TAI_AVIOPUOLISON_KANSSA", 
                "Q10_2_ONKO_BRUTTOTULONNE_ALLE", 
                "Q10_3_ONKO_BRUTTOTULOSI_ALLE")
            .collect(Collectors.toSet());

    @Test
    public void kuljetusapuAllHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/Questionnaire-kuljetusapu.json",
                fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/QuestionnaireResponse-kuljetusapu-hidden.json",
                fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void kuljetusapuVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/Questionnaire-kuljetusapu.json",
                fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/QuestionnaireResponse-kuljetusapu-visible.json",
                fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(Collections.singleton("Q10_3_ONKO_BRUTTOTULOSI_ALLE")));
    }


}
