/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.conversion;

import java.time.Period;

/**
 * SimpleConversion for mapping {@link java.time.Period} to {@link String} and vise versa.
 */
public class JavaPeriodToStringConversion extends AbstractStaticParseToStringConversion {

    @Override
    protected Class<?> getParsingType() {
        return Period.class;
    }

}
