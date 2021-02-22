/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultcomponentmodel;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.mapstruct.factory.Mappers;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-06T16:20:45+0100",
    comments = "version: , compiler: javac, environment: Java 11.0.9.1 (AdoptOpenJDK)"
)
public class NonPublicIterableMapperImpl implements NonPublicIterableMapper {

    private final NonPublicMapper nonPublicMapper = Mappers.getMapper( NonPublicMapper.class );

    @Override
    public List<Target> map(List<Source> list) {
        if ( list == null ) {
            return null;
        }

        List<Target> list1 = new ArrayList<Target>( list.size() );
        for ( Source source : list ) {
            list1.add( nonPublicMapper.map( source ) );
        }

        return list1;
    }
}
