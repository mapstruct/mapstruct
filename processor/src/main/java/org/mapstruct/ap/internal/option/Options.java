/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.option;

import java.util.Locale;
import java.util.Map;

import org.mapstruct.ap.internal.gem.NullValueMappingStrategyGem;
import org.mapstruct.ap.internal.gem.ReportingPolicyGem;

/**
 * The options passed to the code generator.
 *
 * @author Andreas Gudian
 * @author Gunnar Morling
 * @author Filip Hrisafov
 */
public class Options {

    private final Map<String, String> options;

    public Options(Map<String, String> options) {
        this.options = options;
    }

    public boolean isSuppressGeneratorTimestamp() {
        return parseBoolean( MappingOption.SUPPRESS_GENERATOR_TIMESTAMP );
    }

    public boolean isSuppressGeneratorVersionComment() {
        return parseBoolean( MappingOption.SUPPRESS_GENERATOR_VERSION_INFO_COMMENT );
    }

    public ReportingPolicyGem getUnmappedTargetPolicy() {
        return parseEnum( MappingOption.UNMAPPED_TARGET_POLICY, ReportingPolicyGem.class );
    }

    public ReportingPolicyGem getUnmappedSourcePolicy() {
        return parseEnum( MappingOption.UNMAPPED_SOURCE_POLICY, ReportingPolicyGem.class );
    }

    public String getDefaultComponentModel() {
        return options.get( MappingOption.DEFAULT_COMPONENT_MODEL.getOptionName() );
    }

    public String getDefaultInjectionStrategy() {
        return options.get( MappingOption.DEFAULT_INJECTION_STRATEGY.getOptionName() );
    }

    public boolean isAlwaysGenerateSpi() {
        return parseBoolean( MappingOption.ALWAYS_GENERATE_SERVICE_FILE );
    }

    public boolean isDisableBuilders() {
        return parseBoolean( MappingOption.DISABLE_BUILDERS );
    }

    public boolean isVerbose() {
        return parseBoolean( MappingOption.VERBOSE );
    }

    public NullValueMappingStrategyGem getNullValueIterableMappingStrategy() {
        return parseEnum( MappingOption.NULL_VALUE_ITERABLE_MAPPING_STRATEGY, NullValueMappingStrategyGem.class );
    }

    public NullValueMappingStrategyGem getNullValueMapMappingStrategy() {
        return parseEnum( MappingOption.NULL_VALUE_MAP_MAPPING_STRATEGY, NullValueMappingStrategyGem.class );
    }

    public boolean isDisableLifecycleOverloadDeduplicateSelector() {
        return parseBoolean( MappingOption.DISABLE_LIFECYCLE_OVERLOAD_DEDUPLICATE_SELECTOR );
    }

    private boolean parseBoolean(MappingOption option) {
        if ( options.isEmpty() ) {
            return false;
        }
        return Boolean.parseBoolean( options.get( option.getOptionName() ) );
    }

    private <E extends Enum<E>> E parseEnum(MappingOption option, Class<E> enumType) {
        if ( options.isEmpty() ) {
            return null;
        }
        String value = options.get( option.getOptionName() );
        if ( value == null ) {
            return null;
        }
        return Enum.valueOf( enumType, value.toUpperCase( Locale.ROOT ) );
    }
}
