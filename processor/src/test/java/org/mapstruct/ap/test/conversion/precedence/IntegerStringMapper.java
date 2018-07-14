/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.precedence;

public class IntegerStringMapper {

    public String asString(int source) {
        return String.format( "%06d", source );
    }
}
