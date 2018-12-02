package org.mapstruct.ap.internal.conversion;

import java.time.Duration;

/**
 * SimpleConversion for mapping {@link java.time.Duration} to {@link String} and vise versa.
 */
public class JavaDurationToStringConversion extends AbstractStaticParseToStringConversion {

    @Override
    protected Class<?> getParsingType() {
        return Duration.class;
    }

}
