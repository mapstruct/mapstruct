/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2797;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.bugs._2797.model.Example.Person;

import static org.mapstruct.ReportingPolicy.ERROR;

/**
 * @author Ben Zegveld
 */
@Mapper(unmappedTargetPolicy = ERROR)
public interface ExampleMapper {

  @Mapping(target = "personFirstName", source = "names.first")
  @Mapping(target = "personLastName", source = "names.last")
  ExampleDto map(Person person);
}
