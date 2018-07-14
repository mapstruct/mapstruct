/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.abstractclass.generics;

/**
 * @author Andreas Gudian
 *
 */
public class KeyOfAllBeings extends Key {
    private boolean typeParameterIsResolvedToKeyOfAllBeings;

    public boolean typeParameterIsResolvedToKeyOfAllBeings() {
        return typeParameterIsResolvedToKeyOfAllBeings;
    }

    public void setTypeParameterIsResolvedToKeyOfAllBeings(boolean typeParameterIsResolvedToKeyOfAllBeings) {
        this.typeParameterIsResolvedToKeyOfAllBeings = typeParameterIsResolvedToKeyOfAllBeings;
    }
}
