package fi.oda.common.fhir.validation;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hl7.fhir.dstu3.model.*;
import org.hl7.fhir.dstu3.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.dstu3.utils.FHIRPathEngine;
import org.hl7.fhir.exceptions.FHIRException;

public class EnableWhenEvaluator {
    private FHIRPathEngine fhirPathEngine;
    public static final String QUESTIONNAIRE_ENABLEWHEN_MODIFIEREXTENSION = "http://hl7.org/fhir/StructureDefinition/questionnaire-enablewhen";

    public EnableWhenEvaluator(FHIRPathEngine fhirPathEngine) {
        this.fhirPathEngine = fhirPathEngine;
    }

    /**
     * Recursively evaluates a Questionnaire and QuestionnaireResponse for
     * enableWhen conditions.
     * 
     * @param questionnaireResponse
     * @param questionnaire
     * 
     * @return Set of disabled question linkIds (enableWhen conditions not met)
     */
    public Set<String> findDisabledItems(QuestionnaireResponse questionnaireResponse, Questionnaire questionnaire) {
        return questionnaire.getItem()
                .stream()
                .flatMap(item -> findSubItems(item, this::getQuestionnaireItemChildren).stream())
                .filter(subItem -> !isEnabled(subItem, questionnaireResponse))
                .map(QuestionnaireItemComponent::getLinkId)
                .collect(Collectors.toSet());
    }


    public boolean isEnabled(QuestionnaireItemComponent item, QuestionnaireResponse questionnaireResponse) {
        Optional<Extension> enableWhenExtension = getModifierExtension(item,
                QUESTIONNAIRE_ENABLEWHEN_MODIFIEREXTENSION);
        if (enableWhenExtension.isPresent()) {
            String fhirPath = ((StringType) enableWhenExtension.get().getValue()).getValue();
            try {
                boolean result = fhirPathEngine.evaluateToBoolean(questionnaireResponse, questionnaireResponse,
                        fhirPath);
                return result;
            } catch (FHIRException e) {
                throw new RuntimeException("Bad FHIRPath expression", e);
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

    /**
     * Recursively extracts items from a QuestionnaireResponse or a
     * Questionnaire into a list.
     *
     * @param item
     * @param getChildrenFunction
     * @return
     */
    private <T extends BackboneElement> List<T> findSubItems(T item, Function<T, List<T>> getChildrenFunction) {
        List<T> results = getChildrenFunction.apply(item).stream()
                .flatMap(subItem -> findSubItems(subItem, getChildrenFunction).stream()).collect(Collectors.toList());
        results.add(item);
        return results;
    }

    private List<QuestionnaireItemComponent> getQuestionnaireItemChildren(QuestionnaireItemComponent item) {
        return item.getItem();
    }

}
