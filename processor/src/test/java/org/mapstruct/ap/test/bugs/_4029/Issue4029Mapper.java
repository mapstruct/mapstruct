/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4029;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Reproducer for #4029: with {@link CollectionMappingStrategy#ADDER_PREFERRED}, mapping a
 * {@link Map} source property onto a collection target that has both an adder and a setter
 * should fall back to the setter when a direct collection mapping method is available (adder
 * path not applicable for the whole-map mapping).
 */
@Mapper(collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface Issue4029Mapper {

    Issue4029Mapper INSTANCE = Mappers.getMapper( Issue4029Mapper.class );

    Target fromMapSource(MapSource source);

    Target fromListSource(ListSource source);

    /**
     * Direct whole-collection mapping from Map to List. With ADDER_PREFERRED this must still
     * be usable via the setter fallback when the adder path cannot apply.
     */
    default List<String> map(Map<String, String> map) {
        if ( map == null ) {
            return null;
        }
        List<String> result = new ArrayList<>( map.size() );
        for ( Map.Entry<String, String> entry : map.entrySet() ) {
            result.add( entry.getKey() + "=" + entry.getValue() );
        }
        return result;
    }

    class MapSource {
        private Map<String, String> values = new LinkedHashMap<>();

        public Map<String, String> getValues() {
            return values;
        }

        public void setValues(Map<String, String> values) {
            this.values = values;
        }
    }

    class ListSource {
        private List<String> values = new ArrayList<>();

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.values = values;
        }
    }

    class Target {
        private List<String> values;
        private boolean adderUsed;
        private boolean setterUsed;

        public List<String> getValues() {
            return values;
        }

        public void setValues(List<String> values) {
            this.setterUsed = true;
            this.values = values;
        }

        public void addValue(String value) {
            this.adderUsed = true;
            if ( this.values == null ) {
                this.values = new ArrayList<>();
            }
            this.values.add( value );
        }

        public boolean isAdderUsed() {
            return adderUsed;
        }

        public boolean isSetterUsed() {
            return setterUsed;
        }
    }
}
