/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.constants;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(uses = StringListMapper.class)
public interface ErroneousMapper4 {

    ErroneousMapper4 INSTANCE = Mappers.getMapper( ErroneousMapper4.class );

    @Mappings({
        @Mapping(target = "stringConstant", constant = "stringConstant"),
        @Mapping(target = "emptyStringConstant", constant = ""),
        @Mapping(source = "test", target = "integerConstant", expression = "java('test')"),
        @Mapping(target = "longWrapperConstant", constant = "3001L"),
        @Mapping(target = "dateConstant", dateFormat = "dd-MM-yyyy", constant = "09-01-2014"),
        @Mapping(target = "nameConstants", constant = "jack-jill-tom"),
        @Mapping(target = "country", constant = "THE_NETHERLANDS")
    })
    Target sourceToTarget(Source s);
}
