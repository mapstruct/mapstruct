/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;

/**
 * Encapsulates all options specifiable on a mapping method
 *
 * @author Andreas Gudian
 */
public class MappingOptions {
    private Map<String, List<Mapping>> mappings;
    private IterableMapping iterableMapping;
    private MapMapping mapMapping;
    private BeanMapping beanMapping;
    private boolean fullyInitialized;

    public MappingOptions(Map<String, List<Mapping>> mappings, IterableMapping iterableMapping, MapMapping mapMapping,
        BeanMapping beanMapping) {
        this.mappings = mappings;
        this.iterableMapping = iterableMapping;
        this.mapMapping = mapMapping;
        this.beanMapping = beanMapping;
    }

    /**
     * @return the {@link Mapping}s configured for this method, keyed by target property name. Only for enum mapping
     * methods a target will be mapped by several sources.
     */
    public Map<String, List<Mapping>> getMappings() {
        return mappings;
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
     */
    public void applyInheritedOptions(MappingOptions inherited, boolean isInverse, SourceMethod method,
                                      FormattingMessager messager, TypeFactory typeFactory) {
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
                    setBeanMapping( inherited.getBeanMapping() );
                }
            }

            Map<String, List<Mapping>> newMappings = new HashMap<String, List<Mapping>>();

            for ( List<Mapping> lmappings : inherited.getMappings().values() ) {
                for ( Mapping mapping : lmappings ) {
                    if ( isInverse ) {
                        mapping = mapping.reverse( method, messager, typeFactory );
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
            setMappings( newMappings );
        }

        markAsFullyInitialized();
    }
}
