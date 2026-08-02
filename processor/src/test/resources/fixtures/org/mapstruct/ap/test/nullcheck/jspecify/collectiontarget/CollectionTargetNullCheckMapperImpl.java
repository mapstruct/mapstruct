/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.collectiontarget;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-01T21:05:20+0200",
    comments = "version: , compiler: javac, environment: Java 25 (Eclipse Adoptium)"
)
public class CollectionTargetNullCheckMapperImpl implements CollectionTargetNullCheckMapper {

    @Override
    public CollectionTargetNullCheckTargetBean map(CollectionTargetNullCheckSourceBean source) {
        if ( source == null ) {
            return null;
        }

        CollectionTargetNullCheckTargetBean collectionTargetNullCheckTargetBean = new CollectionTargetNullCheckTargetBean();

        List<String> list = integerListToStringList( source.getNumbers() );
        if ( list != null ) {
            collectionTargetNullCheckTargetBean.setNumbers( list );
        }

        return collectionTargetNullCheckTargetBean;
    }

    protected List<String> integerListToStringList(List<Integer> list) {
        if ( list == null ) {
            return null;
        }

        List<String> list1 = new ArrayList<>( list.size() );
        for ( Integer integer : list ) {
            list1.add( String.valueOf( integer ) );
        }

        return list1;
    }
}
