/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2170.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2170.dto.PersonDto;
import org.mapstruct.ap.test.bugs._2170.entity.Person;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface PersonMapper extends EntityMapper<PersonDto, Person> {

}
