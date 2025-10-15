/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.option;

/**
 * @author Filip Hrisafov
 */
public enum MappingOption {

    // CHECKSTYLE:OFF
    SUPPRESS_GENERATOR_TIMESTAMP( "mapstruct.suppressGeneratorTimestamp" ),
    SUPPRESS_GENERATOR_VERSION_INFO_COMMENT( "mapstruct.suppressGeneratorVersionInfoComment" ),
    UNMAPPED_TARGET_POLICY("mapstruct.unmappedTargetPolicy"),
    UNMAPPED_SOURCE_POLICY("mapstruct.unmappedSourcePolicy"),
    DEFAULT_COMPONENT_MODEL("mapstruct.defaultComponentModel"),
    DEFAULT_INJECTION_STRATEGY("mapstruct.defaultInjectionStrategy"),
    ALWAYS_GENERATE_SERVICE_FILE("mapstruct.alwaysGenerateServicesFile"),
    DISABLE_BUILDERS("mapstruct.disableBuilders"),
    VERBOSE("mapstruct.verbose"),
    NULL_VALUE_ITERABLE_MAPPING_STRATEGY("mapstruct.nullValueIterableMappingStrategy"),
    NULL_VALUE_MAP_MAPPING_STRATEGY("mapstruct.nullValueMapMappingStrategy"),
    DISABLE_LIFECYCLE_OVERLOAD_DEDUPLICATE_SELECTOR("mapstruct.disableLifecycleOverloadDeduplicateSelector"),
    ;
    // CHECKSTYLE:ON

    private final String optionName;

    MappingOption(String optionName) {
        this.optionName = optionName;
    }

    /**
     * Returns the name of the option, which can be used in the compiler arguments.
     *
     * @return the name of the option
     */
    public String getOptionName() {
        return optionName;
    }
}
