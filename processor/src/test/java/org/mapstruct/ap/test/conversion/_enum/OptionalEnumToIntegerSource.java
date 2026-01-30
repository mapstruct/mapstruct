/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion._enum;

import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class OptionalEnumToIntegerSource {
    private Optional<EnumToIntegerEnum> enumValue = Optional.empty();

    private Optional<EnumToIntegerEnum> invalidEnumValue = Optional.empty();

    public Optional<EnumToIntegerEnum> getEnumValue() {
        return enumValue;
    }

    public void setEnumValue(Optional<EnumToIntegerEnum> enumValue) {
        this.enumValue = enumValue;
    }

    public Optional<EnumToIntegerEnum> getInvalidEnumValue() {
        return invalidEnumValue;
    }

    public void setInvalidEnumValue(Optional<EnumToIntegerEnum> invalidEnumValue) {
        this.invalidEnumValue = invalidEnumValue;
    }
}
