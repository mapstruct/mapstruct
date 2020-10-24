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
public interface ReturnTypeHasMultipleTypeVarBothGenericMapper {

    ReturnTypeHasMultipleTypeVarBothGenericMapper INSTANCE = Mappers.getMapper( ReturnTypeHasMultipleTypeVarBothGenericMapper.class );

    Target toTarget(Source source);

    default <T, U> HashMap<T, U> toMap( Pair<T, U> entry) {
        HashMap<T, U> result = new HashMap<>(  );
        result.put( entry.first, entry.second );
        return result;
    }

    class Source {

        private Pair<String, Long> prop;

        public Source(Pair<String, Long> prop) {
            this.prop = prop;
        }

        public Pair<String, Long> getProp() {
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

    class Pair<T, U> {
        private final T first;
        private final U second;

        public Pair(T first, U second) {
            this.first = first;
            this.second = second;
        }

        public T getFirst() {
            return first;
        }

        public U getSecond() {
            return second;
        }
    }

}
