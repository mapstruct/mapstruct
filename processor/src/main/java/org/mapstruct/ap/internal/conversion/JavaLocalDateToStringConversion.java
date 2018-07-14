/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

/**
 * Specialization of {@link AbstractJavaTimeToStringConversion} for converting {@link java.time.LocalDate}
 */
public class JavaLocalDateToStringConversion extends AbstractJavaTimeToStringConversion {

    @Override
    protected String defaultFormatterSuffix() {
        return "ISO_LOCAL_DATE";
    }
}
