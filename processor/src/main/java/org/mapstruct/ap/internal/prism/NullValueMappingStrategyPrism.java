/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.prism;

/**
 * Prism for the enum {@link org.mapstruct.NullValueMappingStrategy}
 *
 * @author Sjaak Derksen
 */
public enum NullValueMappingStrategyPrism {

    RETURN_NULL( false ),
    RETURN_DEFAULT( true );

    private final boolean returnDefault;

    NullValueMappingStrategyPrism(boolean returnDefault) {
        this.returnDefault = returnDefault;
    }

    public boolean isReturnDefault() {
        return returnDefault;
    }
}
