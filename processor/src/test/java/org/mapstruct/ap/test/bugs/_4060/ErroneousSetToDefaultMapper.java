/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4060;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper
public interface ErroneousSetToDefaultMapper {

    class WithLocalDate {
        private LocalDate value;

        public LocalDate getValue() {
            return value;
        }

        public void setValue(LocalDate value) {
            this.value = value;
        }
    }

    class WithBigDecimal {
        private BigDecimal value;

        public BigDecimal getValue() {
            return value;
        }

        public void setValue(BigDecimal value) {
            this.value = value;
        }
    }

    class WithComparable {
        private Comparable<String> value;

        public Comparable<String> getValue() {
            return value;
        }

        public void setValue(Comparable<String> value) {
            this.value = value;
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    void updateLocalDate(@MappingTarget WithLocalDate target, WithLocalDate source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    void updateBigDecimal(@MappingTarget WithBigDecimal target, WithBigDecimal source);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT)
    void updateComparable(@MappingTarget WithComparable target, WithComparable source);
}
