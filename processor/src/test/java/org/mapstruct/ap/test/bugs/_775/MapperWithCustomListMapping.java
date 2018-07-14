/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._775;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper
public abstract class MapperWithCustomListMapping {
    public static final MapperWithCustomListMapping INSTANCE =
        Mappers.getMapper( MapperWithCustomListMapping.class );

    public abstract IterableContainer toContainerWithIterable(ListContainer source);

    protected List<Integer> hexListToIntList(Collection<String> source) {
        List<Integer> iterable = new ArrayList<Integer>( source.size() );
        for ( String string : source ) {
            iterable.add( Integer.parseInt( string, 16 ) );
        }

        return iterable;
    }
}
