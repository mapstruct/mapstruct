/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import org.mapstruct.ap.internal.util.JodaTimeConstants;

/**
 * Conversion between {@code LocalDateTime} and {@code String}.
 *
 * @author Timo Eckhardt
 */
public class JodaLocalDateTimeToStringConversion extends AbstractJodaTypeToStringConversion {

    @Override
    protected String formatStyle() {
        return JodaTimeConstants.DATE_TIME_FORMAT;
    }

    @Override
    protected String parseMethod() {
        return "parseLocalDateTime";
    }
}
