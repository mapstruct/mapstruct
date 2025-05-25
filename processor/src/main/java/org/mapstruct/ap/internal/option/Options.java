/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.option;

import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
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
    private final ReportingPolicyGem unmappedSourcePolicy;
    private final boolean alwaysGenerateSpi;
    private final String defaultComponentModel;
    private final String defaultInjectionStrategy;
    private final boolean disableBuilders;
    private final boolean verbose;
    private final NullValueMappingStrategyGem nullValueIterableMappingStrategy;
    private final NullValueMappingStrategyGem nullValueMapMappingStrategy;
    private final boolean disableLifecycleOverloadDeduplicateSelector;

    //CHECKSTYLE:OFF
    public Options(boolean suppressGeneratorTimestamp, boolean suppressGeneratorVersionComment,
                   ReportingPolicyGem unmappedTargetPolicy,
                   ReportingPolicyGem unmappedSourcePolicy,
                   String defaultComponentModel, String defaultInjectionStrategy,
                   boolean alwaysGenerateSpi,
                   boolean disableBuilders,
                   boolean verbose,
                   NullValueMappingStrategyGem nullValueIterableMappingStrategy,
                   NullValueMappingStrategyGem nullValueMapMappingStrategy,
                   boolean disableLifecycleOverloadDeduplicateSelector
                   ) {
        //CHECKSTYLE:ON
        this.suppressGeneratorTimestamp = suppressGeneratorTimestamp;
        this.suppressGeneratorVersionComment = suppressGeneratorVersionComment;
        this.unmappedTargetPolicy = unmappedTargetPolicy;
        this.unmappedSourcePolicy = unmappedSourcePolicy;
        this.defaultComponentModel = defaultComponentModel;
        this.defaultInjectionStrategy = defaultInjectionStrategy;
        this.alwaysGenerateSpi = alwaysGenerateSpi;
        this.disableBuilders = disableBuilders;
        this.verbose = verbose;
        this.nullValueIterableMappingStrategy = nullValueIterableMappingStrategy;
        this.nullValueMapMappingStrategy = nullValueMapMappingStrategy;
        this.disableLifecycleOverloadDeduplicateSelector = disableLifecycleOverloadDeduplicateSelector;
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

    public ReportingPolicyGem getUnmappedSourcePolicy() {
        return unmappedSourcePolicy;
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

    public boolean isDisableBuilders() {
        return disableBuilders;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public NullValueMappingStrategyGem getNullValueIterableMappingStrategy() {
        return nullValueIterableMappingStrategy;
    }

    public NullValueMappingStrategyGem getNullValueMapMappingStrategy() {
        return nullValueMapMappingStrategy;
    }

    public boolean isDisableLifecycleOverloadDeduplicateSelector() {
        return disableLifecycleOverloadDeduplicateSelector;
    }
}
