/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.multiple;

import java.util.HashMap;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface ReturnTypeHasMultipleTypeVarOneGenericMapper {

    ReturnTypeHasMultipleTypeVarOneGenericMapper INSTANCE =
        Mappers.getMapper( ReturnTypeHasMultipleTypeVarOneGenericMapper.class );

    Target toTarget(Source source);

    default <T> HashMap<String, T> toMap( T entry) {
        HashMap<String, T> result = new HashMap<>(  );
        result.put( "test", entry );
        return result;
    }

    class Source {

        private Long prop;

        public Source(Long prop) {
            this.prop = prop;
        }

        public Long getProp() {
            return prop;
        }
    }

    class Target {

        private Map<String, Long> prop;

        public Map<String, Long> getProp() {
            return prop;
        }

        public Target setProp(Map<String, Long> prop) {
            this.prop = prop;
            return this;
        }
    }

}
