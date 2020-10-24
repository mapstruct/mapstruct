/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.plain;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface SourceTypeIsTypeVarMapper {

    SourceTypeIsTypeVarMapper INSTANCE = Mappers.getMapper( SourceTypeIsTypeVarMapper.class );

    Target sourceToTarget(Source source);

    @SuppressWarnings("unchecked")
    default <T> GenericWrapper<T> map( T in ) {
        return new GenericWrapper<>( in );
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

        private GenericWrapper<String> prop;

        public GenericWrapper<String> getProp() {
            return prop;
        }

        public void setProp(GenericWrapper<String> prop) {
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
