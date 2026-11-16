/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2913;

import java.math.BigDecimal;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2913Mapper {

    Issue2913Mapper INSTANCE = Mappers.getMapper( Issue2913Mapper.class );

    @Mapping(target = "doublePrimitiveValue", source = "rounding")
    @Mapping(target = "doubleValue", source = "rounding")
    @Mapping(target = "longPrimitiveValue", source = "rounding")
    @Mapping(target = "longValue", source = "rounding")
    Target map(Source source);

    default Long mapAmount(BigDecimal amount) {
        return amount != null ? amount.movePointRight( 2 ).longValue() : null;
    }

    class Target {

        private double doublePrimitiveValue;
        private Double doubleValue;
        private long longPrimitiveValue;
        private Long longValue;

        public double getDoublePrimitiveValue() {
            return doublePrimitiveValue;
        }

        public void setDoublePrimitiveValue(double doublePrimitiveValue) {
            this.doublePrimitiveValue = doublePrimitiveValue;
        }

        public Double getDoubleValue() {
            return doubleValue;
        }

        public void setDoubleValue(Double doubleValue) {
            this.doubleValue = doubleValue;
        }

        public long getLongPrimitiveValue() {
            return longPrimitiveValue;
        }

        public void setLongPrimitiveValue(long longPrimitiveValue) {
            this.longPrimitiveValue = longPrimitiveValue;
        }

        public Long getLongValue() {
            return longValue;
        }

        public void setLongValue(Long longValue) {
            this.longValue = longValue;
        }
    }

    class Source {

        private final BigDecimal rounding;

        public Source(BigDecimal rounding) {
            this.rounding = rounding;
        }

        public BigDecimal getRounding() {
            return rounding;
        }
    }

}
