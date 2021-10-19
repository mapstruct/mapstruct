/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.gem;

/**
 * Gem for the enum {@link org.mapstruct.SubclassExhaustiveStrategy}
 *
 * @author Ben Zegveld
 */
public enum SubclassExhaustiveStrategyGem {

    COMPILE_ERROR( false ),
    RUNTIME_EXCEPTION( true );

    private final boolean abstractReturnTypeAllowed;

    SubclassExhaustiveStrategyGem(boolean abstractReturnTypeAllowed) {
        this.abstractReturnTypeAllowed = abstractReturnTypeAllowed;
    }

    public boolean isAbstractReturnTypeAllowed() {
        return abstractReturnTypeAllowed;
    }
}
