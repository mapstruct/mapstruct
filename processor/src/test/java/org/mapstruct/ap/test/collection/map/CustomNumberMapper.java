/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.map;

public class CustomNumberMapper {

    public String asString(Long number) {
        return String.valueOf( number );
    }

    public Long asLong(String string) {
        return Long.parseLong( string );
    }
}
