/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._405;

import java.util.List;
import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {EntityFactory.class } )
public abstract class PersonMapper {

    public static final PersonMapper INSTANCE = Mappers.getMapper( PersonMapper.class );

    @Mappings( {
        @Mapping(target = "name",  source = "id") }
    )
    abstract People personToPeople(Person person);

    abstract List<People> personListToPeopleList(List<Person> children);

    abstract Map<Integer, People> personListToPeopleMap(Map<Integer, Person> children);

}
