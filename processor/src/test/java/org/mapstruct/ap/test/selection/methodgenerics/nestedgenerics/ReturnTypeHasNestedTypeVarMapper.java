/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.nestedgenerics;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface ReturnTypeHasNestedTypeVarMapper {

    ReturnTypeHasNestedTypeVarMapper INSTANCE = Mappers.getMapper( ReturnTypeHasNestedTypeVarMapper.class );

    Target toTarget(Source source);

    default <T> List<Set<T>> wrapAsSetInList(T entry) {
        return Collections.singletonList( Collections.singleton( entry ) );
    }

    class Source {

        private final String prop;

        public Source(String prop) {
            this.prop = prop;
        }

        public String getProp() {
            return prop;
        }

    }

    class Target {

        private List<Set<String>> prop;

        public List<Set<String>> getProp() {
            return prop;
        }

        public Target setProp(List<Set<String>> prop) {
            this.prop = prop;
            return this;
        }
    }

}
