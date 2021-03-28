/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.targettype;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface NestedTargetTypeMapper {

    NestedTargetTypeMapper INSTANCE = Mappers.getMapper( NestedTargetTypeMapper.class );

    Target sourceToTarget(Source source);

    @SuppressWarnings("unchecked")
    default <T extends BaseType> T map(String string, @TargetType Class<T> clazz) {
        if ( clazz == GenericWrapper.class ) {
            return (T) new GenericWrapper<>( string );
        }

        return null;
    }

    class Source {

        private String prop;

        public Source(String prop) {
            this.prop = prop;
        }

        public String getProp() {
            return prop;
        }

        public void setProp(String prop) {
            this.prop = prop;
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

    class BaseType {
    }

    class GenericWrapper<T> extends BaseType {
        private final T wrapped;

        public GenericWrapper(T someType) {
            this.wrapped = someType;
        }

        public T getWrapped() {
            return wrapped;
        }
    }
}
