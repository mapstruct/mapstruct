/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedproperties.simple.source;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;

public class SourceRoot {

    private SourceProps props;

    private final Optional<String> someString;
    private final Optional<Integer> someInteger;
    private final Optional<Double> someDouble;
    private final Optional<Boolean> someBoolean;
    private final OptionalInt someIntValue;
    private final OptionalDouble someDoubleValue;
    private final OptionalLong someLongValue;
    
    public SourceRoot(Optional<String> someString, Optional<Integer> someInteger, Optional<Double> someDouble,
                      Optional<Boolean> someBoolean, OptionalInt someIntValue, OptionalDouble someDoubleValue, OptionalLong someLongValue) {
        this.someString = someString;
        this.someInteger = someInteger;
        this.someDouble = someDouble;
        this.someBoolean = someBoolean;
        this.someIntValue = someIntValue;
        this.someDoubleValue = someDoubleValue;
        this.someLongValue = someLongValue;
    }

    public void setProps(SourceProps props) {
        this.props = props;
    }

    public SourceProps getProps() {
        return props;
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

    public OptionalDouble getSomeDoubleValue() {
        return someDoubleValue;
    }

    public OptionalInt getSomeIntValue() {
        return someIntValue;
    }

    public OptionalLong getSomeLongValue() {
        return someLongValue;
    }
}
