/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.enumtointeger;

public class EnumToIntegerSource {
    private EnumToIntegerEnum enumValue;

    private EnumToIntegerEnum invalidEnumValue;

    public EnumToIntegerEnum getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(final EnumToIntegerEnum enumValue) {
        this.enumValue = enumValue;
    }

    public EnumToIntegerEnum getInvalidEnumValue() {
        return invalidEnumValue;
    }

    public void setInvalidEnumValue(EnumToIntegerEnum invalidEnumValue) {
        this.invalidEnumValue = invalidEnumValue;
    }
}
