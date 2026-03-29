/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.wildcard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-03-29T19:42:23+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class SuperBoundCopyMapperImpl implements SuperBoundCopyMapper {

    @Override
    public CollectionSuperTypes copyCollection(CollectionSuperTypes collectionSuperTypes) {
        if ( collectionSuperTypes == null ) {
            return null;
        }

        CollectionSuperTypes collectionSuperTypes1 = new CollectionSuperTypes();

        Collection<? super SimpleObject> collection = collectionSuperTypes.getSimpleObjectsCollection();
        if ( collection != null ) {
            collectionSuperTypes1.setSimpleObjectsCollection( new ArrayList<>( collection ) );
        }

        return collectionSuperTypes1;
    }

    @Override
    public MapSuperType copyMap(MapSuperType mapSuperType) {
        if ( mapSuperType == null ) {
            return null;
        }

        MapSuperType mapSuperType1 = new MapSuperType();

        Map<? super SimpleObject, ? super SimpleObject> map = mapSuperType.getSimpleMap();
        if ( map != null ) {
            mapSuperType1.setSimpleMap( new LinkedHashMap<>( map ) );
        }

        return mapSuperType1;
    }
}
