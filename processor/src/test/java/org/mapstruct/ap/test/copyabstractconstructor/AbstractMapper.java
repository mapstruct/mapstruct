/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.copyabstractconstructor;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public abstract class AbstractMapper {

    public AbstractMapper(String value) {

    }

    @Mapping( target = "updatedOnTarget", source = "updatedOn" )
    abstract Target map(Source source);

}
