/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3057;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Ben Zegveld
 */
@Mapper
public interface SelfMapper {

    class Source {
        public Source self;
    }

    class Target {
        public Target self;
        public String value;
    }

    @Mapping( target = "value", constant = "constantValue" )
    Target map(Source source);

    SelfMapper INSTANCE = Mappers.getMapper( SelfMapper.class );
}
