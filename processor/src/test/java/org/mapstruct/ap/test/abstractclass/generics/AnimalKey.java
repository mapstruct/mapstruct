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
public class AnimalKey extends KeyOfAllBeings {
    private boolean typeParameterIsResolvedToAnimalKey;

    public boolean typeParameterIsResolvedToAnimalKey() {
        return typeParameterIsResolvedToAnimalKey;
    }

    public void setTypeParameterIsResolvedToAnimalKey(boolean typeParameterIsResolvedToAnimalKey) {
        this.typeParameterIsResolvedToAnimalKey = typeParameterIsResolvedToAnimalKey;
    }
}
