package fi.oda.common.fhir.validation.utils;

import static fi.oda.common.fhir.validation.QuestionnaireUtils.findSubItems;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.hl7.fhir.r4.elementmodel.Element;
import org.hl7.fhir.r4.model.*;
import org.hl7.fhir.r4.model.Questionnaire.QuestionnaireItemComponent;
import org.hl7.fhir.r4.model.QuestionnaireResponse.QuestionnaireResponseItemComponent;

import fi.oda.common.fhir.validation.*;

public class QuestionnaireEnableWhenEvaluator {
    private EnableWhenEvaluator enableWhenEvaluator;

    public QuestionnaireEnableWhenEvaluator(EnableWhenEvaluator enableWhenEvaluator) {
        this.enableWhenEvaluator = enableWhenEvaluator;
    }

    public Set<String> findDisabledItems(Element questionnaireResponse, Questionnaire questionnaire) {
        return questionnaire.getItem().stream()
                .flatMap(item -> findSubItems(item, this::getQuestionnaireItemChildren).stream())
                .filter(subItem -> !enableWhenEvaluator.isEnabled(subItem, questionnaireResponse))
                .map(QuestionnaireItemComponent::getLinkId).collect(Collectors.toSet());
    }
    


    public <T extends BackboneElement> List<T> findSubItems(T item, Function<T, List<T>> getChildrenFunction) {
        List<T> results = getChildrenFunction.apply(item).stream()
                .flatMap(subItem -> findSubItems(subItem, getChildrenFunction).stream()).collect(Collectors.toList());
        results.add(item);
        return results;
    }

    public List<QuestionnaireItemComponent> getQuestionnaireItemChildren(QuestionnaireItemComponent item) {
        return item.getItem();
    }

    public List<QuestionnaireResponseItemComponent> getQuestionnaireResponseItemChildren(
            QuestionnaireResponseItemComponent item) {
        return item.getItem();
    }

}
