/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.array;

import java.lang.reflect.Array;
import java.util.Collections;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface ReturnTypeIsTypeVarArrayMapper {

    ReturnTypeIsTypeVarArrayMapper INSTANCE = Mappers.getMapper( ReturnTypeIsTypeVarArrayMapper.class );

    Target sourceToTarget(Source source);

    @SuppressWarnings("unchecked")
    default <T> T[] map(GenericWrapper<T> in ) {
        return Collections.singletonList( in.getWrapped() )
                          .toArray( (T[] ) Array.newInstance( in.getWrapped().getClass(), 1 ) );
    }

    class Source {

        private GenericWrapper<String> prop;

        public Source(GenericWrapper<String> prop) {
            this.prop = prop;
        }

        public GenericWrapper<String> getProp() {
            return prop;
        }

        public void setProp(GenericWrapper<String> prop) {
            this.prop = prop;
        }
    }

    class Target {

        private String[] prop;

        public String[] getProp() {
            return prop;
        }

        public void setProp(String[] prop) {
            this.prop = prop;
        }
    }

    class GenericWrapper<T> {
        private final T wrapped;

        public GenericWrapper(T someType) {
            this.wrapped = someType;
        }

        public T getWrapped() {
            return wrapped;
        }
    }
}
