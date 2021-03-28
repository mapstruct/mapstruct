/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.methodgenerics.targettype;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.TargetType;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper
public interface PlainTargetTypeMapper {

    PlainTargetTypeMapper INSTANCE = Mappers.getMapper( PlainTargetTypeMapper.class );

    Target sourceToTarget(Source source);

    @SuppressWarnings("unchecked")
    default <T> T map(String string, @TargetType Class<T> clazz) {
        if ( clazz == BigDecimal.class ) {
            return (T) new BigDecimal( string );
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

        private BigDecimal prop;

        public BigDecimal getProp() {
            return prop;
        }

        public void setProp(BigDecimal prop) {
            this.prop = prop;
        }
    }

    class BaseType {
    }

}
