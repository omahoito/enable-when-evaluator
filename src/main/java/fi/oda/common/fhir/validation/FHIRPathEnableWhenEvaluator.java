package fi.oda.common.fhir.validation;

import java.util.Optional;
import org.hl7.fhir.exceptions.FHIRException;
import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.model.Extension;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.utils.FHIRPathEngine;

public class FHIRPathEnableWhenEvaluator extends DefaultEnableWhenEvaluator {
    private FHIRPathEngine fhirPathEngine;
    public static final String QUESTIONNAIRE_ENABLEWHEN_MODIFIEREXTENSION = "http://hl7.org/fhir/StructureDefinition/questionnaire-enablewhen";

    public FHIRPathEnableWhenEvaluator(FHIRPathEngine fhirPathEngine) {
        this.fhirPathEngine = fhirPathEngine;
    }

    @Override
    public boolean isEnabled(QuestionnaireItemComponent questionnareItem,
            Element questionnaireResponse) {
        Optional<Extension> enableWhenExtension = getModifierExtension(questionnareItem,
                QUESTIONNAIRE_ENABLEWHEN_MODIFIEREXTENSION);
        if (enableWhenExtension.isPresent()) {
            String fhirPath = ((org.hl7.fhir.r4.model.StringType) enableWhenExtension.get().getValue()).getValue();
            try {               
                return fhirPathEngine.evaluateToBoolean(null, questionnaireResponse, fhirPath);
            } catch (FHIRException e) {
                throw new RuntimeException("Error evaluating FHIRPath", e);
            }
        }
        return true;
    }
    
    private Optional<Extension> getModifierExtension(QuestionnaireItemComponent item, String url) {
        if (item.hasModifierExtension()) {
            return item.getModifierExtension().stream().filter(e -> e.getUrl().equals(url)).findFirst();
        }
        return Optional.empty();
    }
}
