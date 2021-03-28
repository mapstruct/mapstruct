/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.nestedgenerics;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTypeHasNestedTypeVarMapper {

    SourceTypeHasNestedTypeVarMapper INSTANCE = Mappers.getMapper( SourceTypeHasNestedTypeVarMapper.class );

    Target toTarget(Source source);

    default <T> T unwrapToOneElement(List<Set<T>> listOfSet) {
        if ( !listOfSet.isEmpty() && listOfSet.get( 0 ).iterator().hasNext() ) {
            return listOfSet.get( 0 ).iterator().next();
        }
        return null;
    }

    class Source {

        private final List<Set<String>> prop;

        public Source(List<Set<String>> prop) {
            this.prop = prop;
        }

        public List<Set<String>> getProp() {
            return prop;
        }

    }

    class Target {

        private String prop;

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
        }
    }

}
