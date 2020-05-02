/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.nametransformation;

import org.mapstruct.ap.spi.EnumTransformationStrategy;

/**
 * @author Filip Hrisafov
 */
public class CustomEnumTransformationStrategy implements EnumTransformationStrategy {

    @Override
    public String getType() {
        return "custom";
    }

    @Override
    public String transform(String value, String configuration) {
        return value.toLowerCase() + configuration;
    }
}
