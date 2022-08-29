/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion._enum;

public class EnumToIntegerTarget {
    private Integer enumValue;

    private Integer invalidEnumValue;

    public Integer getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(final Integer enumValue) {
        this.enumValue = enumValue;
    }

    public Integer getInvalidEnumValue() {
        return invalidEnumValue;
    }

    public void setInvalidEnumValue(Integer invalidEnumValue) {
        this.invalidEnumValue = invalidEnumValue;
    }
}
