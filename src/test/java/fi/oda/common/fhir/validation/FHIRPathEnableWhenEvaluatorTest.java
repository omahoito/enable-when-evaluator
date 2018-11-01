package fi.oda.common.fhir.validation;

import static fi.oda.common.fhir.validation.TestUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

import java.util.*;

import org.hl7.fhir.dstu3.context.IWorkerContext;
import org.hl7.fhir.dstu3.hapi.ctx.*;
import org.hl7.fhir.dstu3.hapi.validation.DefaultProfileValidationSupport;
import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.utils.FHIRPathEngine;
import org.junit.Test;

import ca.uhn.fhir.context.FhirContext;

public class FHIRPathEnableWhenEvaluatorTest {
    
    private final String QUESTINNAIRE_FOLDER = "questionnaire_fhirpath";
    private final String QUESTINNAIRERESPONSE_FOLDER = "questionnaireresponse";

    private final FhirContext fhirContext = FhirContext.forDstu3();
    private final IValidationSupport validationSupport = new DefaultProfileValidationSupport();
    private final IWorkerContext workerContext = new HapiWorkerContext(fhirContext, validationSupport);
    private final FHIRPathEngine fhirPathEngine = new FHIRPathEngine(workerContext);
    private final EnableWhenEvaluator enableWhenEvaluator = new FHIRPathEnableWhenEvaluator(fhirPathEngine);
    private final QuestionnaireEnableWhenEvaluator questionnaireEvaluator = new QuestionnaireEnableWhenEvaluator(
            enableWhenEvaluator);
    private final Collection<String> expectedHidden = Collections.singleton("Q_CONDITIONAL");

    @Test
    public void choiceAnswerHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/codingtest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void choiceAnswerVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/choicetest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/codingtest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void integerComparatorHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/comparatortest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/comparatortest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void integerComparatorVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/comparatortest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/comparatortest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void multiCriteriaVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void multiCriteriaHiddenFhirPath1() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-hidden1.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void multiCriteriaHiddenFhirPath2() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-hidden2.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }

    @Test
    public void multiCriteriaHiddenFhirPath3() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIRE_FOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSE_FOLDER + "/multicriteria-response-hidden3.json", questionnaire, fhirContext);

        Set<String> disabled = questionnaireEvaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(expectedHidden));
    }
}
