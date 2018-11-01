package fi.oda.common.fhir.validation;

import static fi.oda.common.fhir.validation.TestUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.*;

import org.hl7.fhir.dstu3.model.*;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;

public class DefaultEnableWhenEvaluatorTest {
    
    private final String QUESTINNAIRE_FOLDER = "questionnaire_enablewhen";
    private final String QUESTINNAIRERESPONSE_FOLDER = "questionnaireresponse";

    private final FhirContext fhirContext = FhirContext.forDstu3();
    private final DefaultEnableWhenEvaluator enableWhenEvaluator = new DefaultEnableWhenEvaluator();
    private final QuestionnaireEnableWhenEvaluator questionnaireEvaluator = new QuestionnaireEnableWhenEvaluator(
            enableWhenEvaluator);
    private final Collection<String> expectedHidden = Collections.singleton("Q_CONDITIONAL");

    @Test
    public void choiceAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/choicetest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void choiceAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/choicetest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void booleanAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/booleantest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/booleantest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void booleanAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/booleantest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/booleantest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void stringAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/stringtest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void stringAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/stringtest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void textAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/texttest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void textAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/texttest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/stringtest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void integerAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/integertest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/integertest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void integerAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/integertest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/integertest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void uriAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/uritest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/uritest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void uriAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/uritest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/uritest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void decimalAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/decimaltest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/decimaltest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void decimalAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/decimaltest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/decimaltest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }
    
    @Test
    public void dateAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/datetest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void dateAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/datetest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void dateTimeAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetimetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/datetimetest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void dateTimeAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/datetimetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/datetimetest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void timeAnswerVisibleEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/timetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/timetest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void timeAnswerHiddenEnableWhen() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/timetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/timetest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }
}
