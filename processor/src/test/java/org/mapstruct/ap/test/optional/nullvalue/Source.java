/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvalue;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public class Source {

    private final String someString;
    private final Integer someInteger;
    private final Double someDouble;
    private final Boolean someBoolean;
    private final Integer someIntValue;
    private final Double someDoubleValue;
    private final Long someLongValue;

    public Source(String someString, Integer someInteger, Double someDouble, Boolean someBoolean, Integer someIntValue,
                  Double someDoubleValue, Long someLongValue) {
        this.someString = someString;
        this.someInteger = someInteger;
        this.someDouble = someDouble;
        this.someBoolean = someBoolean;
        this.someIntValue = someIntValue;
        this.someDoubleValue = someDoubleValue;
        this.someLongValue = someLongValue;
    }

    public Optional<String> getSomeString() {
        return Optional.ofNullable( someString );
    }

    public Optional<Integer> getSomeInteger() {
        return Optional.ofNullable( someInteger );
    }

    public Optional<Double> getSomeDouble() {
        return Optional.ofNullable( someDouble );
    }

    public Optional<Boolean> getSomeBoolean() {
        return Optional.ofNullable( someBoolean );
    }

    public OptionalDouble getSomeDoubleValue() {
        return someDouble != null ? OptionalDouble.of( someDoubleValue ) : OptionalDouble.empty();
    }

    public OptionalInt getSomeIntValue() {
        return someIntValue != null ? OptionalInt.of( someIntValue ) : OptionalInt.empty();
    }

    public OptionalLong getSomeLongValue() {
        return someLongValue != null ? OptionalLong.of( someLongValue ) : OptionalLong.empty();
    }
}
