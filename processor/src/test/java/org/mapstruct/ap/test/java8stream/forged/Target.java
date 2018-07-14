/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import java.util.Set;
import java.util.stream.Stream;

public class Target {

    private Set<Long> fooStream;
    private Stream<Long> fooStream2;
    private Stream<Long> fooStream3;

    public Set<Long> getFooStream() {
        return fooStream;
    }

    public void setFooStream(Set<Long> fooStream) {
        this.fooStream = fooStream;
    }

    public Stream<Long> getFooStream2() {
        return fooStream2;
    }

    public void setFooStream2(Stream<Long> fooStream2) {
        this.fooStream2 = fooStream2;
    }

    public Stream<Long> getFooStream3() {
        return fooStream3;
    }

    public void setFooStream3(Stream<Long> fooStream3) {
        this.fooStream3 = fooStream3;
    }
}
