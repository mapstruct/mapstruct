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
    date = "2026-03-30T02:05:36+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class BoundCopyMapperImpl implements BoundCopyMapper {

    @Override
    public CollectionSuperTypes copySuperCollection(CollectionSuperTypes collectionSuperTypes) {
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
    public CollectionExtendTypes copyExtendsCollection(CollectionExtendTypes collectionExtendTypes) {
        if ( collectionExtendTypes == null ) {
            return null;
        }

        CollectionExtendTypes collectionExtendTypes1 = new CollectionExtendTypes();

        Collection<? extends SimpleObject> collection = collectionExtendTypes.getSimpleObjectsCollection();
        if ( collection != null ) {
            collectionExtendTypes1.setSimpleObjectsCollection( new ArrayList<>( collection ) );
        }

        return collectionExtendTypes1;
    }

    @Override
    public MapSuperType copySuperMap(MapSuperType mapSuperType) {
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

    @Override
    public MapExtendType copyExtendMap(MapExtendType mapExtendType) {
        if ( mapExtendType == null ) {
            return null;
        }

        MapExtendType mapExtendType1 = new MapExtendType();

        Map<? extends SimpleObject, ? extends SimpleObject> map = mapExtendType.getSimpleMap();
        if ( map != null ) {
            mapExtendType1.setSimpleMap( new LinkedHashMap<>( map ) );
        }

        return mapExtendType1;
    }
}
