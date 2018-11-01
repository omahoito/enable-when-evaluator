package fi.oda.common.fhir.validation;

import java.util.*;

import org.hl7.fhir.dstu3.model.Extension;

public class EnableWhenResult {
    private final boolean enabled;
    private final List<Extension> modifierExtensions;
    private final String linkId;

    /**
     * Evaluation result of enableWhen condition
     * 
     * @param enabled
     *            Evaluation result
     * @param linkId
     *            LinkId of the questionnaire item
     * @param modifierExtensions
     *            Modifier extensions of the enableWhen element. These can be
     *            checked in a custom evaluator.
     */
    public EnableWhenResult(boolean enabled, String linkId, List<Extension> modifierExtensions) {
        this.enabled = enabled;
        this.linkId = linkId;
        this.modifierExtensions = modifierExtensions;
    }

    public EnableWhenResult(boolean enabled, String linkId) {
        this(enabled, linkId, Collections.emptyList());
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getLinkId() {
        return linkId;
    }

    public List<Extension> getModifierExtensions() {
        return modifierExtensions;
    }
}
