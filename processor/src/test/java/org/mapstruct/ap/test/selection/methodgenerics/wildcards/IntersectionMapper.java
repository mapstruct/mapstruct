/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.wildcards;

import java.io.Serializable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IntersectionMapper {

    IntersectionMapper INSTANCE = Mappers.getMapper( IntersectionMapper.class );

    Target map( Source source);

    default <T extends TypeB & Serializable> T unwrap(Wrapper<? extends T> t) {
        return t.getWrapped();
    }

    class Source {

        private final Wrapper<TypeC> prop;

        public Source(Wrapper<TypeC> prop) {
            this.prop = prop;
        }

        public Wrapper<TypeC> getProp() {
            return prop;
        }

    }

    class Wrapper<T> {

        private final T wrapped;

        public Wrapper(T wrapped) {
            this.wrapped = wrapped;
        }

        public T getWrapped() {
            return wrapped;
        }

    }

    class Target {

        private TypeC prop;

        public TypeC getProp() {
            return prop;
        }

        public void setProp(TypeC prop) {
            this.prop = prop;
        }
    }

    /**
     * TypeC must intersect both TypeB & Serializable
     */
    class TypeC extends TypeB implements Serializable {
    }

    class TypeB extends TypeA {
    }

    class TypeA {
    }

}
