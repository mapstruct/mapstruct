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
public interface MapToBeanNonStringMapUsedInExpressionMapper {

    MapToBeanNonStringMapUsedInExpressionMapper INSTANCE =
        Mappers.getMapper( MapToBeanNonStringMapUsedInExpressionMapper.class );

    @Mapping(target = "sum", expression = "java(calculateSum( values ))")
    Target toTarget(String value, Map<Integer, Integer> values);

    default int calculateSum(Map<Integer, Integer> values) {
        int sum = 0;
        for ( Integer value : values.values() ) {
            sum += value;
        }
        return sum;
    }

    class Target {

        private final String value;
        private final int sum;

        public Target(String value, int sum) {
            this.value = value;
            this.sum = sum;
        }

        public String getValue() {
            return value;
        }

        public int getSum() {
            return sum;
        }
    }

}
