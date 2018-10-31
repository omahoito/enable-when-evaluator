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

public class EnableWhenEvaluatorTest {
    
    private final String QUESTINNAIREFOLDER = "questionnaires";
    private final String QUESTINNAIRERESPONSEFOLDER = "questionnaireresponses";

    private final FhirContext fhirContext = FhirContext.forDstu3();
    private final IValidationSupport validationSupport = new DefaultProfileValidationSupport();
    private final IWorkerContext workerContext = new HapiWorkerContext(fhirContext, validationSupport);
    private final FHIRPathEngine fhirPathEngine = new FHIRPathEngine(workerContext);
    private final EnableWhenEvaluator evaluator = new EnableWhenEvaluator(fhirPathEngine);

    @Test
    public void codingAnswerHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/codingtest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/codingtest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(Collections.singleton("Q_CONDITIONAL")));
    }

    @Test
    public void codingAnswerVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/codingtest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/codingtest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void integerComparatorHiddenFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/comparatortest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/comparatortest-response-hidden.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(Collections.singleton("Q_CONDITIONAL")));
    }

    @Test
    public void integerComparatorVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/comparatortest.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/comparatortest-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void multicriteriaVisibleFhirPath() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/multicriteria-response-visible.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, empty());
    }

    @Test
    public void multicriteriaHiddenFhirPath1() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/multicriteria-response-hidden1.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(Collections.singleton("Q_CONDITIONAL")));
    }

    @Test
    public void multicriteriaHiddenFhirPath2() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/multicriteria-response-hidden2.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(Collections.singleton("Q_CONDITIONAL")));
    }

    @Test
    public void multicriteriaHiddenFhirPath3() {
        Questionnaire questionnaire = readQuestionnaire(QUESTINNAIREFOLDER + "/multicriteria.json", fhirContext);
        QuestionnaireResponse questionnaireresponse = (QuestionnaireResponse) readQuestionnaireResponse(
                QUESTINNAIRERESPONSEFOLDER + "/multicriteria-response-hidden3.json", questionnaire, fhirContext);

        Set<String> disabled = evaluator.findDisabledItems(questionnaireresponse, questionnaire);

        assertThat(disabled, equalTo(Collections.singleton("Q_CONDITIONAL")));
    }
}
