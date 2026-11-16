/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct;

/**
 * Strategy for defining what to what a condition (check) method is applied to
 *
 * @author Filip Hrisafov
 * @since 1.6
 */
public enum ConditionStrategy {
    /**
     * The condition method should be applied whether a property should be mapped.
     */
    PROPERTIES,
    /**
     * The condition method should be applied to check if a source parameters should be mapped.
     */
    SOURCE_PARAMETERS,
}
