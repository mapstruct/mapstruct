/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.forged;

import java.util.stream.Stream;

public class ErroneousNonMappableStreamTarget {

    private Stream<Bar> nonMappableStream;

    public Stream<Bar> getNonMappableStream() {
        return nonMappableStream;
    }

    public void setNonMappableStream(Stream<Bar> nonMappableStream) {
        this.nonMappableStream = nonMappableStream;
    }

}
