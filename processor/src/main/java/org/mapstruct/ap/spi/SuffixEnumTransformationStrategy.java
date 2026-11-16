/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * An {@link EnumTransformationStrategy} that appends a suffix to the enum value.
 *
 * @author Filip Hrisafov
 *
 * @since 1.4
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
