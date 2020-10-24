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
public interface BothParameterizedMapper {

    BothParameterizedMapper INSTANCE = Mappers.getMapper( BothParameterizedMapper.class );

    Target sourceToTarget(Source source);

    default <T> GenericTargetWrapper<T> map(GenericSourceWrapper<T> in ) {
        return new GenericTargetWrapper<>( in.getWrapped() );
    }

    class Source {

        private final GenericSourceWrapper<String> prop;

        public <T> Source(GenericSourceWrapper<String> prop) {
            this.prop = prop;
        }

        public GenericSourceWrapper<String> getProp() {
            return prop;
        }

    }

    class Target {

        private GenericTargetWrapper<String> prop;

        public GenericTargetWrapper<String> getProp() {
            return prop;
        }

        public void setProp(GenericTargetWrapper<String> prop) {
            this.prop = prop;
        }
    }

    class GenericTargetWrapper<T> {
        private final T wrapped;

        public GenericTargetWrapper(T someType) {
            this.wrapped = someType;
        }

        public T getWrapped() {
            return wrapped;
        }
    }

    class GenericSourceWrapper<T> {
        private final T wrapped;

        public GenericSourceWrapper(T someType) {
            this.wrapped = someType;
        }

        public T getWrapped() {
            return wrapped;
        }
    }
}
