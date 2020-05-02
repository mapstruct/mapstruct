/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * @author Filip Hrisafov
 */
public class StripPrefixEnumTransformationStrategy implements EnumTransformationStrategy {

    @Override
    public String getType() {
        return "stripPrefix";
    }

    @Override
    public String transform(String value, String configuration) {
        if ( value.startsWith( configuration ) ) {
            return value.substring( configuration.length() );
        }
        return value;
    }
}
