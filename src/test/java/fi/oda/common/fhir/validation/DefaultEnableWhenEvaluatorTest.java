package fi.oda.common.fhir.validation;

import static fi.oda.common.fhir.validation.utils.TestUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.exceptions.*;
import org.hl7.fhir.r4.context.IWorkerContext;
import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.elementmodel.JsonParser;
import org.hl7.fhir.r4.elementmodel.ParserBase.ValidationPolicy;
import org.hl7.fhir.r4.hapi.ctx.*;
import org.hl7.fhir.r4.model.BackboneElement;
import org.hl7.fhir.r4.model.BaseResource;
import org.hl7.fhir.r4.model.IdType;
import org.hl7.fhir.r4.model.Questionnaire;
import org.hl7.fhir.r4.model.Reference;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.QuestionnaireResponse.QuestionnaireResponseItemComponent;
import org.hl7.fhir.r4.utils.FHIRPathEngine;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;
import fi.oda.common.fhir.validation.utils.QuestionnaireEnableWhenEvaluator;
import static fi.oda.common.fhir.validation.utils.TestUtils.*;
public class DefaultEnableWhenEvaluatorTest extends TestBase {    
    
    private final String QUESTINNAIRE_FOLDER = "questionnaire_enablewhen";
    private final String QUESTINNAIRERESPONSE_FOLDER = "questionnaireResponse";
    private final EnableWhenEvaluator enableWhenEvaluator = new DefaultEnableWhenEvaluator();
    private final QuestionnaireEnableWhenEvaluator  questionnaireEvaluator = new QuestionnaireEnableWhenEvaluator(enableWhenEvaluator);
            
    private final Collection<String> expectedHidden = Collections.singleton("Q_CONDITIONAL");

    @Test
    public void choiceAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/choicetest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void choiceAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/choicetest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void booleanAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/booleantest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/booleantest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void booleanAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/booleantest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/booleantest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void stringAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/stringtest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void stringAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/stringtest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void textAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/texttest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void textAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/texttest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void integerAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/integertest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/integertest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void integerAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/integertest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/integertest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }
/*
    @Test
    public void uriAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/uritest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/uritest-response-visible.json", fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void uriAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/uritest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/uritest-response-hidden.json", fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }
    */

    @Test
    public void decimalAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/decimaltest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/decimaltest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void decimalAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/decimaltest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/decimaltest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }
    
    @Test
    public void dateAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/datetest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void dateAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/datetest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void dateTimeAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetimetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/datetimetest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void dateTimeAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetimetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/datetimetest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void timeAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/timetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/timetest-response-visible.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void timeAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/timetest.json", fhirContext);
        Element questionnaireResponse = readQuestionnaireResponseElement(
                QUESTINNAIRERESPONSE_FOLDER + "/timetest-response-hidden.json", fhirContext, parser);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireResponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

}
