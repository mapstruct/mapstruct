/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.nametransformation;

// tag::documentation[]

import org.mapstruct.ap.spi.EnumTransformationStrategy;

// end::documentation[]

/**
 * @author Filip Hrisafov
 */
// tag::documentation[]
public class CustomEnumTransformationStrategy implements EnumTransformationStrategy {

    @Override
    public String getStrategyName() {
        return "custom";
    }

    @Override
    public String transform(String value, String configuration) {
        return value.toLowerCase() + configuration;
    }
}
// end::documentation[]
