package fi.oda.common.fhir.validation;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


import org.apache.commons.io.IOUtils;
import org.hl7.fhir.exceptions.*;
import org.hl7.fhir.r4.elementmodel.*;
import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.elementmodel.ParserBase.ValidationPolicy;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.QuestionnaireResponse.QuestionnaireResponseItemComponent;
import org.hl7.fhir.r4.utils.FHIRPathEngine;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import fi.oda.common.fhir.validation.utils.QuestionnaireEnableWhenEvaluator;

import static fi.oda.common.fhir.validation.utils.TestUtils.*;

public class FHIRPathEnableWhenEvaluatorTest extends TestBase{
    
    private final String QUESTINNAIRE_FOLDER = "questionnaire_fhirpath";
    private final String QUESTINNAIRERESPONSE_FOLDER = "questionnaireResponse";

    private final FHIRPathEngine fhirPathEngine = new FHIRPathEngine(workerContext);
    private final EnableWhenEvaluator enableWhenEvaluator = new FHIRPathEnableWhenEvaluator(fhirPathEngine);
    private final QuestionnaireEnableWhenEvaluator questionnaireEvaluator = new QuestionnaireEnableWhenEvaluator(
            enableWhenEvaluator);
    private final Collection<String> expectedHidden = Collections.singleton("Q_CONDITIONAL");

    @Test
    public void choiceAnswerHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        Element questionnaireResponse =  readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/choicetest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void choiceAnswerVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/choicetest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void integerComparatorHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/comparatortest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/comparatortest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void integerComparatorVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/comparatortest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/comparatortest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void multiCriteriaVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void multiCriteriaHiddenFhirPath1() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-hidden1.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void multiCriteriaHiddenFhirPath2() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-hidden2.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void multiCriteriaHiddenFhirPath3() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-hidden3.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }    

}
