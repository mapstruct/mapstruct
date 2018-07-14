/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.streamtononiterable;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StringListMapper {

    public String stringListToString(Stream<String> strings) {
        return strings == null ? null : strings.collect( Collectors.joining( "-" ) );
    }

    public Stream<String> stringToStringList(String string) {
        return string == null ? null : Arrays.asList( string.split( "-" ) ).stream();
    }
}
