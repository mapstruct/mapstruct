/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._515;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class Issue515Mapper {

    public static final Issue515Mapper INSTANCE = Mappers.getMapper( Issue515Mapper.class );

    @Mapping( target = "id", expression = "java(\"blah)\\\"\")" )
    public abstract Target map(Source source);

}
