/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2673;

import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface Issue2673Mapper {

    Issue2673Mapper INSTANCE = Mappers.getMapper( Issue2673Mapper.class );

    Target map(Source source);

    default <T> T map(Optional<T> opt) {
        return opt.orElse( null );
    }

    default <T> Optional<T> map(T t) {
        return Optional.ofNullable( t );
    }

    class Target {
        private final int primitive;
        private final String nonPrimitive;

        public Target(int primitive, String nonPrimitive) {
            this.primitive = primitive;
            this.nonPrimitive = nonPrimitive;
        }

        public int getPrimitive() {
            return primitive;
        }

        public String getNonPrimitive() {
            return nonPrimitive;
        }
    }

    class Source {
        private final int primitive;
        private final Optional<String> nonPrimitive;

        public Source(int primitive, Optional<String> nonPrimitive) {
            this.primitive = primitive;
            this.nonPrimitive = nonPrimitive;
        }

        public int getPrimitive() {
            return primitive;
        }

        public Optional<String> getNonPrimitive() {
            return nonPrimitive;
        }
    }
}
