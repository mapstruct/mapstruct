/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import java.util.stream.Stream;

public class ErroneousNonMappableStreamSource {

    private Stream<Foo> nonMappableStream;

    public Stream<Foo> getNonMappableStream() {
        return nonMappableStream;
    }

    public void setNonMappableStream(Stream<Foo> nonMappableStream) {
        this.nonMappableStream = nonMappableStream;
    }

}
