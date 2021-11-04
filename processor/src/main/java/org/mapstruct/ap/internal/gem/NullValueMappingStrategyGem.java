/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.gem;

/**
 * Gem for the enum {@link org.mapstruct.NullValueMappingStrategy}
 *
 * @author Sjaak Derksen
 */
public enum NullValueMappingStrategyGem {

    RETURN_NULL( false , false),
    RETURN_DEFAULT( true, false ),
    RETURN_NULL_ON_ALL_NULL_VALUES ( false , true);

    private final boolean returnDefault;

    private final boolean returnNullOnAllNullValues;

    NullValueMappingStrategyGem(boolean returnDefault, boolean returnNullOnAllNull) {
        this.returnDefault = returnDefault;
        this.returnNullOnAllNullValues = returnNullOnAllNull;
    }

    public boolean isReturnDefault() {
        return returnDefault;
    }

    public boolean isReturnNullOnAllNullValues() {
        return returnNullOnAllNullValues;
    }

}
