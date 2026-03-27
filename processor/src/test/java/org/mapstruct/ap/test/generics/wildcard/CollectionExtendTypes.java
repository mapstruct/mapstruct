/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import java.util.Collection;

public class CollectionExtendTypes {

    private Collection<? extends SimpleObject> simpleObjectsCollection;

    public Collection<? extends SimpleObject> getSimpleObjectsCollection() {
        return simpleObjectsCollection;
    }

    public void setSimpleObjectsCollection(Collection<? extends SimpleObject> simpleObjectsCollection) {
        this.simpleObjectsCollection = simpleObjectsCollection;
    }
}
