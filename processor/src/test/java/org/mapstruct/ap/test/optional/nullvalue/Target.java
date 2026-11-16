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

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class Target {

    private final Optional<String> someString;
    private final Optional<Integer> someInteger;
    private final Optional<Double> someDouble;
    private final Optional<Boolean> someBoolean;
    private final OptionalInt someIntValue;
    private final OptionalDouble someDoubleValue;
    private final OptionalLong someLongValue;

    public Target(Optional<String> someString, Optional<Integer> someInteger, Optional<Double> someDouble,
                  Optional<Boolean> someBoolean, OptionalInt someIntValue, OptionalDouble someDoubleValue,
                  OptionalLong someLongValue) {
        this.someString = someString;
        this.someInteger = someInteger;
        this.someDouble = someDouble;
        this.someBoolean = someBoolean;
        this.someIntValue = someIntValue;
        this.someDoubleValue = someDoubleValue;
        this.someLongValue = someLongValue;
    }

    public Optional<String> getSomeString() {
        return someString;
    }

    public Optional<Integer> getSomeInteger() {
        return someInteger;
    }

    public Optional<Double> getSomeDouble() {
        return someDouble;
    }

    public Optional<Boolean> getSomeBoolean() {
        return someBoolean;
    }

    public OptionalLong getSomeLongValue() {
        return someLongValue;
    }

    public OptionalDouble getSomeDoubleValue() {
        return someDoubleValue;
    }

    public OptionalInt getSomeIntValue() {
        return someIntValue;
    }
}
