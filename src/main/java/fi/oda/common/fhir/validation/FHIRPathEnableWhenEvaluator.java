package fi.oda.common.fhir.validation;

import java.util.Optional;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.dstu3.utils.FHIRPathEngine;
import org.hl7.fhir.exceptions.FHIRException;

public class FHIRPathEnableWhenEvaluator extends DefaultEnableWhenEvaluator {
    private FHIRPathEngine fhirPathEngine;
    public static final String QUESTIONNAIRE_ENABLEWHEN_MODIFIEREXTENSION = "http://hl7.org/fhir/StructureDefinition/questionnaire-enablewhen";

    public FHIRPathEnableWhenEvaluator(FHIRPathEngine fhirPathEngine) {
        this.fhirPathEngine = fhirPathEngine;
    }

    @Override
    public boolean isEnabled(QuestionnaireItemComponent questionnaireItem,
            QuestionnaireResponse questionnaireResponse) {
        Optional<Extension> enableWhenExtension = getModifierExtension(questionnaireItem,
                QUESTIONNAIRE_ENABLEWHEN_MODIFIEREXTENSION);
        if (enableWhenExtension.isPresent()) {
            String fhirPath = ((StringType) enableWhenExtension.get().getValue()).getValue();
            try {
                return fhirPathEngine.evaluateToBoolean(questionnaireResponse, questionnaireResponse, fhirPath);
            } catch (FHIRException e) {
                throw new RuntimeException("Error evaluating FHIRPath", e);
            }
        }
        return super.isEnabled(questionnaireItem, questionnaireResponse);
    }

    private Optional<Extension> getModifierExtension(QuestionnaireItemComponent item, String url) {
        if (item.hasModifierExtension()) {
            return item.getModifierExtension().stream().filter(e -> e.getUrl().equals(url)).findFirst();
        }
        return Optional.empty();
    }

}
