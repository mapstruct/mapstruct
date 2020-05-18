/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * @author Filip Hrisafov
 */
public class StripSuffixEnumTransformationStrategy implements EnumTransformationStrategy {

    @Override
    public String getStrategyName() {
        return "stripSuffix";
    }

    @Override
    public String transform(String value, String configuration) {
        if ( value.endsWith( configuration ) ) {
            return value.substring( 0, value.length() - configuration.length() );
        }
        return value;
    }
}
