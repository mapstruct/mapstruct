/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import java.util.stream.Stream;

public class Source {

    private Stream<SourceFoo> fooStream;

    public Stream<SourceFoo> getFooStream() {
        return fooStream;
    }

    public void setFooStream(Stream<SourceFoo> fooStream) {
        this.fooStream = fooStream;
    }

}
