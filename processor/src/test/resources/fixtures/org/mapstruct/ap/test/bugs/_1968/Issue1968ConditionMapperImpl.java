/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1968;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-11-23T22:30:00+0300",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (N/A)"
)
public class Issue1968ConditionMapperImpl implements Issue1968ConditionMapper {

    @Override
    public Animal dtoToEntity(AnimalDto dto) {
        if ( dto == null ) {
            return null;
        }

        Animal animal = new Animal();

        if ( isNotEmpty( dto.getOtherNames() ) ) {
            animal.setSpecialNames( stringArrayToStringList( dto.getOtherNames() ) );
        }
        animal.setNames( stringArrayToStringList( dto.getNames() ) );

        return animal;
    }

    protected List<String> stringArrayToStringList(String[] stringArray) {
        if ( stringArray == null ) {
            return null;
        }

        List<String> list = new ArrayList<>( stringArray.length );
        for ( String string : stringArray ) {
            list.add( string );
        }

        return list;
    }
}
