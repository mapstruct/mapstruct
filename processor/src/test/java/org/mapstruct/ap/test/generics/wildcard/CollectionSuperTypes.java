/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import java.util.Collection;

public class CollectionSuperTypes {

    private Collection<? super SimpleObject> simpleObjectsCollection;

    public Collection<? super SimpleObject> getSimpleObjectsCollection() {
        return simpleObjectsCollection;
    }

    public void setSimpleObjectsCollection(Collection<? super SimpleObject> simpleObjectsCollection) {
        this.simpleObjectsCollection = simpleObjectsCollection;
    }
}
