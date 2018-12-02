package org.mapstruct.ap.internal.conversion;

import java.time.Instant;

/**
 * SimpleConversion for mapping {@link java.time.Instant} to {@link String} and vise versa.
 */
public class JavaInstantToStringConversion extends AbstractStaticParseToStringConversion {

    @Override
    protected Class<?> getParsingType() {
        return Instant.class;
    }

}
