/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.erroneous;

import java.util.stream.Stream;

public class Source {

    private Stream<String> strings;

    public Stream<String> getStrings() {
        return strings;
    }

    public void setStrings(Stream<String> strings) {
        this.strings = strings;
    }
}
