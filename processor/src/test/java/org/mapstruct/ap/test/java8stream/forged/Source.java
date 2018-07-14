/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import java.util.Set;
import java.util.stream.Stream;

public class Source {

    private Stream<String> fooStream;
    private Set<String> fooStream2;
    private Stream<String> fooStream3;

    public Stream<String> getFooStream() {
        return fooStream;
    }

    public void setFooStream(Stream<String> fooStream) {
        this.fooStream = fooStream;
    }

    public Set<String> getFooStream2() {
        return fooStream2;
    }

    public void setFooStream2(Set<String> fooStream2) {
        this.fooStream2 = fooStream2;
    }

    public Stream<String> getFooStream3() {
        return fooStream3;
    }

    public void setFooStream3(Stream<String> fooStream3) {
        this.fooStream3 = fooStream3;
    }
}
