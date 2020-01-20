/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.option;

import org.mapstruct.ap.internal.gem.ReportingPolicyGem;

/**
 * The options passed to the code generator.
 *
 * @author Andreas Gudian
 * @author Gunnar Morling
 */
public class Options {
    private final boolean suppressGeneratorTimestamp;
    private final boolean suppressGeneratorVersionComment;
    private final ReportingPolicyGem unmappedTargetPolicy;
    private final boolean alwaysGenerateSpi;
    private final String defaultComponentModel;
    private final String defaultInjectionStrategy;
    private final boolean verbose;

    public Options(boolean suppressGeneratorTimestamp, boolean suppressGeneratorVersionComment,
                   ReportingPolicyGem unmappedTargetPolicy,
                   String defaultComponentModel, String defaultInjectionStrategy,
                   boolean alwaysGenerateSpi, boolean verbose) {
        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.suppressGeneratorVersionComment = suppressGeneratorVersionComment;
        this.unmappedTargetPolicy = unmappedTargetPolicy;
        this.defaultComponentModel = defaultComponentModel;
        this.defaultInjectionStrategy = defaultInjectionStrategy;
        this.alwaysGenerateSpi = alwaysGenerateSpi;
        this.verbose = verbose;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return suppressGeneratorTimestamp;
    }

    public boolean isSuppressGeneratorVersionComment() {
        return suppressGeneratorVersionComment;
    }

    public ReportingPolicyGem getUnmappedTargetPolicy() {
        return unmappedTargetPolicy;
    }

    public String getDefaultComponentModel() {
        return defaultComponentModel;
    }

    public String getDefaultInjectionStrategy() {
        return defaultInjectionStrategy;
    }

    public boolean isAlwaysGenerateSpi() {
        return alwaysGenerateSpi;
    }

    public boolean isVerbose() {
        return verbose;
    }
}
