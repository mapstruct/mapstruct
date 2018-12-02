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
