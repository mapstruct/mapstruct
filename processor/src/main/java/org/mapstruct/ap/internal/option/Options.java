/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.option;

import org.mapstruct.ap.internal.prism.ComponentModelPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;

/**
 * The options passed to the code generator.
 *
 * @author Andreas Gudian
 * @author Gunnar Morling
 */
public class Options {
    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final ReportingPolicyPrism unmappedTargetPolicy;
    private final boolean alwaysGenerateSpi;
    private final ComponentModelPrism defaultComponentModel;
    private final boolean verbose;

    public Options(boolean suppressGeneratorTimestamp, boolean suppressGeneratorVersionComment,
                   ReportingPolicyPrism unmappedTargetPolicy,
                   String defaultComponentModel, boolean alwaysGenerateSpi, boolean verbose) {
        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.suppressGeneratorVersionComment = suppressGeneratorVersionComment;
        this.unmappedTargetPolicy = unmappedTargetPolicy;
        this.defaultComponentModel = defaultComponentModel == null ? null :
                                            ComponentModelPrism.valueOf( defaultComponentModel.toUpperCase() );
        this.alwaysGenerateSpi = alwaysGenerateSpi;
        this.verbose = verbose;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return suppressGeneratorVersionComment;
    }

    public ReportingPolicyPrism getUnmappedTargetPolicy() {
        return unmappedTargetPolicy;
    }

    public ComponentModelPrism getDefaultComponentModel() {
        return defaultComponentModel;
    }

    public boolean isAlwaysGenerateSpi() {
        return alwaysGenerateSpi;
    }

    public boolean isVerbose() {
        return verbose;
    }
}
