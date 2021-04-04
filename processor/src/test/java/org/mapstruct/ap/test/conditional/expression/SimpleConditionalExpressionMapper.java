/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.expression;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface SimpleConditionalExpressionMapper {

    SimpleConditionalExpressionMapper INSTANCE = Mappers.getMapper( SimpleConditionalExpressionMapper.class );

    @Mapping(target = "value", conditionExpression = "java(source.getValue() < 100)")
    Target map(Source source);

    class Source {
        private final int value;

        public Source(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    class Target {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }
}
