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
        private final int a;
        private final String b;

        public Target(int a, String b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public String getB() {
            return b;
        }
    }

    class Source {
        private final int a;
        private final Optional<String> b;

        public Source(int a, Optional<String> b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public Optional<String> getB() {
            return b;
        }
    }
}
