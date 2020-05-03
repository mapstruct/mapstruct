/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * @author Filip Hrisafov
 */
public class SuffixEnumTransformationStrategy implements EnumTransformationStrategy {

    @Override
    public String getStrategyName() {
        return "suffix";
    }

    @Override
    public String transform(String value, String configuration) {
        return value + configuration;
    }
}
