/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.streamtononiterable;

import java.util.stream.Stream;

public class Source {

    private Stream<String> names;

    public Stream<String> getNames() {
        return names;
    }

    public void setNames(Stream<String> names) {
        this.names = names;
    }
}
