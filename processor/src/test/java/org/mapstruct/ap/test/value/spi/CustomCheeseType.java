/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.spi;

/**
 * @author Filip Hrisafov
 */
public enum CustomCheeseType implements CustomEnumMarker {

    UNSPECIFIED,
    CUSTOM_BRIE,
    CUSTOM_ROQUEFORT,
    UNRECOGNIZED,
}
