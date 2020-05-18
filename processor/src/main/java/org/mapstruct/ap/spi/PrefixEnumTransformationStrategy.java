/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * @author Filip Hrisafov
 */
public class PrefixEnumTransformationStrategy implements EnumTransformationStrategy {

    @Override
    public String getStrategyName() {
        return "prefix";
    }

    @Override
    public String transform(String value, String configuration) {
        return configuration + value;
    }
}
