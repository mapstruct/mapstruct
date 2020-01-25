/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.util.accessor.Accessor;

import static org.mapstruct.ap.internal.model.source.Mapping.getMappingTargetNamesBy;

/**
 * Encapsulates all options specifiable on a mapping method
 *
 * @author Andreas Gudian
 */
public class MappingMethodOptions {
    private static final MappingMethodOptions EMPTY = new MappingMethodOptions( Collections.emptySet(),
        null,
        null,
        null,
        Collections.emptyList()
    );

    private Set<Mapping> mappings;
    private IterableMappingOptions iterableMapping;
    private MapMappingOptions mapMapping;
    private BeanMappingOptions beanMapping;
    private List<ValueMapping> valueMappings;
    private boolean fullyInitialized;

    public MappingMethodOptions(Set<Mapping> mappings, IterableMappingOptions iterableMapping, MapMappingOptions mapMapping,
                                BeanMappingOptions beanMapping, List<ValueMapping> valueMappings ) {
        this.mappings = mappings;
        this.iterableMapping = iterableMapping;
        this.mapMapping = mapMapping;
        this.beanMapping = beanMapping;
        this.valueMappings = valueMappings;
    }

    /**
     * creates empty mapping options
     *
     * @return empty mapping options
     */
    public static MappingMethodOptions empty() {
        return EMPTY;
    }

    /**
     * @return the {@link Mapping}s configured for this method, keyed by target property name. Only for enum mapping
     * methods a target will be mapped by several sources. TODO. Remove the value list when 2.0
     */
    public Set<Mapping> getMappings() {
        return mappings;
    }

    public IterableMappingOptions getIterableMapping() {
        return iterableMapping;
    }

    public MapMappingOptions getMapMapping() {
        return mapMapping;
    }

    public BeanMappingOptions getBeanMapping() {
        return beanMapping;
    }

    public List<ValueMapping> getValueMappings() {
        return valueMappings;
    }

    public void setIterableMapping(IterableMappingOptions iterableMapping) {
        this.iterableMapping = iterableMapping;
    }

    public void setMapMapping(MapMappingOptions mapMapping) {
        this.mapMapping = mapMapping;
    }

    public void setBeanMapping(BeanMappingOptions beanMapping) {
        this.beanMapping = beanMapping;
    }

    public void setValueMappings(List<ValueMapping> valueMappings) {
        this.valueMappings = valueMappings;
    }

    /**
     * @return the {@code true}, iff the options have been fully initialized by applying all available inheritance
     * options
     */
    public boolean isFullyInitialized() {
        return fullyInitialized;
    }

    public void markAsFullyInitialized() {
        this.fullyInitialized = true;
    }

    /**
     * Merges in all the mapping options configured, giving the already defined options precedence.
     *
     * @param templateMethod the template method with the options to inherit, may be {@code null}
     * @param isInverse if {@code true}, the specified options are from an inverse method
     * @param method the source method
     */
    public void applyInheritedOptions(SourceMethod templateMethod, boolean isInverse, SourceMethod method ) {
        MappingMethodOptions inherited = templateMethod.getMappingOptions();
        if ( null != inherited ) {
            if ( getIterableMapping() == null && inherited.getIterableMapping() != null) {
                setIterableMapping( inherited.getIterableMapping() );
            }

            if ( getMapMapping() == null && inherited.getMapMapping() != null) {
                setMapMapping( inherited.getMapMapping() );
            }

            if ( getBeanMapping() == null && inherited.getBeanMapping() != null ) {
                setBeanMapping( BeanMappingOptions.forInheritance( inherited.getBeanMapping() ) );
            }

            if ( getValueMappings() == null ) {
                if ( inherited.getValueMappings() != null ) {
                    // there were no mappings, so the inherited mappings are the new ones
                    setValueMappings( inherited.getValueMappings() );
                }
                else {
                    setValueMappings( Collections.emptyList() );
                }
            }
            else {
                if ( inherited.getValueMappings() != null ) {
                    // iff there are also inherited mappings, we inverse and add them.
                    for ( ValueMapping inheritedValueMapping : inherited.getValueMappings() ) {
                        ValueMapping valueMapping = isInverse ? inheritedValueMapping.inverse() : inheritedValueMapping;
                        if ( valueMapping != null
                            && !getValueMappings().contains(  valueMapping ) ) {
                            getValueMappings().add( valueMapping );
                        }
                    }
                }
            }

            Set<Mapping> newMappings = new LinkedHashSet<>();
            for ( Mapping mapping : inherited.getMappings() ) {
                if ( isInverse ) {
                    if ( mapping.canInverse() ) {
                        newMappings.add( mapping.copyForInverseInheritance( templateMethod ) );
                    }
                }
                else {
                    newMappings.add( mapping.copyForForwardInheritance( templateMethod ) );
                }
            }

            // now add all (does not override duplicates and leaves original mappings)
            mappings.addAll( newMappings );

            // filter new mappings
            filterNestedTargetIgnores( mappings );
        }
    }

    public void applyIgnoreAll(SourceMethod method, TypeFactory typeFactory ) {
        CollectionMappingStrategyPrism cms = method.getMapperConfiguration().getCollectionMappingStrategy();
        Type writeType = method.getResultType();
        if ( !method.isUpdateMethod() ) {
            writeType = typeFactory.effectiveResultTypeFor(
                            writeType,
                            BeanMappingOptions.builderPrismFor( method )
            );
        }
        Map<String, Accessor> writeAccessors = writeType.getPropertyWriteAccessors( cms );


        Set<String> mappedPropertyNames = mappings.stream()
                                                  .map( m -> getPropertyEntries( m )[0] )
                                                  .collect( Collectors.toSet() );

        for ( String targetPropertyName : writeAccessors.keySet() ) {
            if ( !mappedPropertyNames.contains( targetPropertyName ) ) {
                Mapping mapping = Mapping.forIgnore( targetPropertyName );
                mappings.add( mapping );
            }
        }
    }

    private void filterNestedTargetIgnores( Set<Mapping> mappings) {

        // collect all properties to ignore, and safe their target name ( == same name as first ref target property)
        Set<String> ignored = getMappingTargetNamesBy( Mapping::isIgnored, mappings );
        mappings.removeIf( m -> isToBeIgnored( ignored, m ) );
    }

    private boolean isToBeIgnored(Set<String> ignored, Mapping mapping) {
        String[] propertyEntries = getPropertyEntries( mapping );
        return propertyEntries.length > 1 && ignored.contains( propertyEntries[ 0 ] );
    }

    private String[] getPropertyEntries( Mapping mapping ) {
        return mapping.getTargetName().split( "\\." );
    }

}
