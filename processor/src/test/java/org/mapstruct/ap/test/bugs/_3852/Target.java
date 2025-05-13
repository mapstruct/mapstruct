/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3852;

import java.util.Optional;

/**
 * @author Dennis Melzer
 */
public class Target {

    private final Optional<String> someString;
    private final Optional<Integer> someInteger;
    private final Optional<Double> someDouble;
    private final Optional<Boolean> someBoolean;

    public Target(Optional<String> someString, Optional<Integer> someInteger, Optional<Double> someDouble,
                  Optional<Boolean> someBoolean) {
        this.someString = someString;
        this.someInteger = someInteger;
        this.someDouble = someDouble;
        this.someBoolean = someBoolean;
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

}
