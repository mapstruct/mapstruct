/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.frommap;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapToBeanNonStringMapUsedInDefaultExpressionMapper {

    MapToBeanNonStringMapUsedInDefaultExpressionMapper INSTANCE =
        Mappers.getMapper( MapToBeanNonStringMapUsedInDefaultExpressionMapper.class );

    @Mapping(target = "sum", source = "source.sum", defaultExpression = "java(calculateSum( values ))")
    Target toTarget(Source source, Map<Integer, Integer> values);

    default int calculateSum(Map<Integer, Integer> values) {
        int sum = 0;
        for ( Integer value : values.values() ) {
            sum += value;
        }
        return sum;
    }

    class Source {

        private final Integer sum;

        public Source(Integer sum) {
            this.sum = sum;
        }

        public Integer getSum() {
            return sum;
        }
    }

    class Target {

        private Integer sum;

        public Integer getSum() {
            return sum;
        }

        public void setSum(Integer sum) {
            this.sum = sum;
        }
    }

}
