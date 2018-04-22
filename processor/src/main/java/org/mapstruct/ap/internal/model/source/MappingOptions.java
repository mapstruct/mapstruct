/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.internal.model.source;

import static org.mapstruct.ap.internal.util.Collections.first;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.prism.CollectionMappingStrategyPrism;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.accessor.Accessor;

/**
 * Encapsulates all options specifiable on a mapping method
 *
 * @author Andreas Gudian
 */
public class MappingOptions {
    private static final MappingOptions EMPTY = new MappingOptions( Collections.<String, List<Mapping>>emptyMap(),
        null,
        null,
        null,
        Collections.<ValueMapping>emptyList(),
        false
    );
    private Map<String, List<Mapping>> mappings;
    private IterableMapping iterableMapping;
    private MapMapping mapMapping;
    private BeanMapping beanMapping;
    private List<ValueMapping> valueMappings;
    private boolean fullyInitialized;
    private final boolean restrictToDefinedMappings;

    public MappingOptions(Map<String, List<Mapping>> mappings, IterableMapping iterableMapping, MapMapping mapMapping,
        BeanMapping beanMapping, List<ValueMapping> valueMappings, boolean restrictToDefinedMappings ) {
        this.mappings = mappings;
        this.iterableMapping = iterableMapping;
        this.mapMapping = mapMapping;
        this.beanMapping = beanMapping;
        this.valueMappings = valueMappings;
        this.restrictToDefinedMappings = restrictToDefinedMappings;
    }

    /**
     * creates empty mapping options
     *
     * @return empty mapping options
     */
    public static MappingOptions empty() {
        return EMPTY;
    }

    /**
     * creates mapping options with only regular mappings
     *
     * @param mappings regular mappings to add
     * @param restrictToDefinedMappings whether to restrict the mappings only to the defined mappings
     * @return MappingOptions with only regular mappings
     */
    public static MappingOptions forMappingsOnly(Map<String, List<Mapping>> mappings,
        boolean restrictToDefinedMappings) {
        return forMappingsOnly( mappings, restrictToDefinedMappings, restrictToDefinedMappings );

    }

    /**
     * creates mapping options with only regular mappings
     *
     * @param mappings regular mappings to add
     * @param restrictToDefinedMappings whether to restrict the mappings only to the defined mappings
     * @param forForgedMethods whether the mappings are for forged methods
     * @return MappingOptions with only regular mappings
     */
    public static MappingOptions forMappingsOnly(Map<String, List<Mapping>> mappings,
        boolean restrictToDefinedMappings, boolean forForgedMethods) {
        return new MappingOptions(
            mappings,
            null,
            null,
            forForgedMethods ? BeanMapping.forForgedMethods() : null,
            Collections.<ValueMapping>emptyList(),
            restrictToDefinedMappings
        );

    }

    /**
     * @return the {@link Mapping}s configured for this method, keyed by target property name. Only for enum mapping
     * methods a target will be mapped by several sources. TODO. Remove the value list when 2.0
     */
    public Map<String, List<Mapping>> getMappings() {
        return mappings;
    }

    /**
     * Check there are nested target references for this mapping options.
     *
     * @return boolean, true if there are nested target references
     */
    public boolean hasNestedTargetReferences() {
        for ( List<Mapping> mappingList : mappings.values() ) {
            for ( Mapping mapping : mappingList ) {
                TargetReference targetReference = mapping.getTargetReference();
                if ( targetReference.isValid() && targetReference.getPropertyEntries().size() > 1 ) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     *
     * @return all dependencies to other properties the contained mappings are dependent on
     */
    public List<String> collectNestedDependsOn() {

        List<String> nestedDependsOn = new ArrayList<String>();
        for ( List<Mapping> mappingList : mappings.values() ) {
            for ( Mapping mapping : mappingList ) {
                nestedDependsOn.addAll( mapping.getDependsOn() );
            }
        }
        return nestedDependsOn;
    }

    /**
     * Initializes the underlying mappings with a new property. Specifically used in in combination with forged methods
     * where the new parameter name needs to be established at a later moment.
     *
     * @param sourceParameter the new source parameter
     */
    public void initWithParameter( Parameter sourceParameter ) {
        for ( List<Mapping> mappingList : mappings.values() ) {
            for ( Mapping mapping : mappingList )  {
                mapping.init( sourceParameter );
            }
        }
    }

    public IterableMapping getIterableMapping() {
        return iterableMapping;
    }

    public MapMapping getMapMapping() {
        return mapMapping;
    }

    public BeanMapping getBeanMapping() {
        return beanMapping;
    }

    public List<ValueMapping> getValueMappings() {
        return valueMappings;
    }

    public void setMappings(Map<String, List<Mapping>> mappings) {
        this.mappings = mappings;
    }

    public void setIterableMapping(IterableMapping iterableMapping) {
        this.iterableMapping = iterableMapping;
    }

    public void setMapMapping(MapMapping mapMapping) {
        this.mapMapping = mapMapping;
    }

    public void setBeanMapping(BeanMapping beanMapping) {
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
     * @param inherited the options to inherit, may be {@code null}
     * @param isInverse if {@code true}, the specified options are from an inverse method
     * @param method the source method
     * @param messager the messager
     * @param typeFactory the type factory
     * @param accessorNaming the accessor naming utils
     */
    public void applyInheritedOptions(MappingOptions inherited, boolean isInverse, SourceMethod method,
                                      FormattingMessager messager, TypeFactory typeFactory,
                                      AccessorNamingUtils accessorNaming) {
        if ( null != inherited ) {
            if ( getIterableMapping() == null ) {
                if ( inherited.getIterableMapping() != null ) {
                    setIterableMapping( inherited.getIterableMapping() );
                }
            }

            if ( getMapMapping() == null ) {
                if ( inherited.getMapMapping() != null ) {
                    setMapMapping( inherited.getMapMapping() );
                }
            }

            if ( getBeanMapping() == null ) {
                if ( inherited.getBeanMapping() != null ) {
                    setBeanMapping( BeanMapping.forInheritance( inherited.getBeanMapping() ) );
                }
            }

            if ( getValueMappings() == null ) {
                if ( inherited.getValueMappings() != null ) {
                    // there were no mappings, so the inherited mappings are the new ones
                    setValueMappings( inherited.getValueMappings() );
                }
                else {
                    setValueMappings( Collections.<ValueMapping>emptyList() );
                }
            }
            else {
                if ( inherited.getValueMappings() != null ) {
                    // iff there are also inherited mappings, we reverse and add them.
                    for ( ValueMapping inheritedValueMapping : inherited.getValueMappings() ) {
                        ValueMapping valueMapping = isInverse ? inheritedValueMapping.reverse() : inheritedValueMapping;
                        if ( valueMapping != null
                            && !getValueMappings().contains(  valueMapping ) ) {
                            getValueMappings().add( valueMapping );
                        }
                    }
                }

            }

            Map<String, List<Mapping>> newMappings = new HashMap<String, List<Mapping>>();

            for ( List<Mapping> lmappings : inherited.getMappings().values() ) {
                for ( Mapping mapping : lmappings ) {
                    if ( isInverse ) {
                        mapping = mapping.reverse( method, messager, typeFactory, accessorNaming );
                    }

                    if ( mapping != null ) {
                        List<Mapping> mappingsOfProperty = newMappings.get( mapping.getTargetName() );
                        if ( mappingsOfProperty == null ) {
                            mappingsOfProperty = new ArrayList<Mapping>();
                            newMappings.put( mapping.getTargetName(), mappingsOfProperty );
                        }

                        mappingsOfProperty.add( mapping.copyForInheritanceTo( method ) );
                    }
                }
            }


            // now add all of its own mappings
            newMappings.putAll( getMappings() );

            // filter new mappings
            filterNestedTargetIgnores( newMappings );

            setMappings( newMappings );
        }
    }

    public void applyIgnoreAll(MappingOptions inherited, SourceMethod method, FormattingMessager messager,
                               TypeFactory typeFactory, AccessorNamingUtils accessorNaming) {
        CollectionMappingStrategyPrism cms = method.getMapperConfiguration().getCollectionMappingStrategy();
        Type writeType = method.getResultType();
        if ( !method.isUpdateMethod() ) {
            writeType = writeType.getEffectiveType();
        }
        Map<String, Accessor> writeAccessors = writeType.getPropertyWriteAccessors( cms );
        List<String> mappedPropertyNames = new ArrayList<String>();
        for ( String targetMappingName : mappings.keySet() ) {
            mappedPropertyNames.add( targetMappingName.split( "\\." )[0] );
        }
        for ( String targetPropertyName : writeAccessors.keySet() ) {
            if ( !mappedPropertyNames.contains( targetPropertyName ) ) {
                Mapping mapping = Mapping.forIgnore( targetPropertyName );
                mapping.init( method, messager, typeFactory, accessorNaming );
                mappings.put( targetPropertyName, Arrays.asList( mapping ) );
            }
        }
    }

    private void filterNestedTargetIgnores( Map<String, List<Mapping>> mappings) {

        // collect all properties to ignore, and safe their target name ( == same name as first ref target property)
        Set<String> ignored = new HashSet<String>();
        for ( Map.Entry<String, List<Mapping>> mappingEntry : mappings.entrySet() ) {
            Mapping mapping = first( mappingEntry.getValue() ); // list only used for deprecated enums mapping
            if ( mapping.isIgnored() && mapping.getTargetReference().isValid() ) {
                ignored.add( mapping.getTargetName() );
            }
        }

        // collect all entries to remove (avoid concurrent modification)
        Set<String> toBeRemoved = new HashSet<String>();
        for ( Map.Entry<String, List<Mapping>> mappingEntry : mappings.entrySet() ) {
            Mapping mapping = first( mappingEntry.getValue() ); // list only used for deprecated enums mapping
            TargetReference targetReference = mapping.getTargetReference();
            if ( targetReference.isValid()
                && targetReference.getPropertyEntries().size() > 1
                && ignored.contains( first( targetReference.getPropertyEntries() ).getName() ) ) {
                toBeRemoved.add( mappingEntry.getKey() );
            }
        }

        // finall remove all duplicates
        mappings.keySet().removeAll( toBeRemoved );

    }

    public boolean isRestrictToDefinedMappings() {
        return restrictToDefinedMappings;
    }

}
