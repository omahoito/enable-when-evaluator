package fi.oda.common.fhir.validation;

import java.util.List;

import java.util.stream.Collectors;

import org.hl7.fhir.r4.elementmodel.Element;

public class QuestionnaireUtils {

    public static List<Element> findSubItems(Element item) {
        List<Element> results = item.getChildren("item").stream().flatMap(i -> findSubItems(i).stream()).collect(Collectors.toList());
        results.add(item);
        return results;
    }

    public static boolean hasLinkId(Element item, String linkId) {
        Element linkIdChild = item.getNamedChild("linkId");
        if (linkIdChild != null && linkIdChild.getValue().equals(linkId)){
            return true;
        }
        return false;
    }

}
