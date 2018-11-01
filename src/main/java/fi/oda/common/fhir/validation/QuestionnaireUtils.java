package fi.oda.common.fhir.validation;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hl7.fhir.dstu3.model.BackboneElement;
import org.hl7.fhir.dstu3.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.dstu3.model.QuestionnaireResponse.QuestionnaireResponseItemComponent;

public class QuestionnaireUtils {

    /**
     * Recursively extracts items from a QuestionnaireResponse or a
     * Questionnaire into a list.
     *
     * @param item
     * @param getChildrenFunction
     * @return
     */
    public static <T extends BackboneElement> List<T> findSubItems(T item, Function<T, List<T>> getChildrenFunction) {
        List<T> results = getChildrenFunction.apply(item).stream()
                .flatMap(subItem -> findSubItems(subItem, getChildrenFunction).stream()).collect(Collectors.toList());
        results.add(item);
        return results;
    }

    public static List<QuestionnaireItemComponent> getQuestionnaireItemChildren(QuestionnaireItemComponent item) {
        return item.getItem();
    }

    public static List<QuestionnaireResponseItemComponent> getQuestionnaireResponseItemChildren(
            QuestionnaireResponseItemComponent item) {
        return item.getItem();
    }

}
