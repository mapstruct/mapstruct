/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

/**
 * Conversion between {@code LocalTime} and {@code String}.
 *
 * @author Timo Eckhardt
 */
public class JodaLocalTimeToStringConversion extends AbstractJodaTypeToStringConversion {

    @Override
    protected String formatStyle() {
        return "-L";
    }

    @Override
    protected String parseMethod() {
        return "parseLocalTime";
    }
}
