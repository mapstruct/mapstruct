/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1339;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(uses = {
    Callback.class
})
public interface Issue1339Mapper {

    Issue1339Mapper INSTANCE = Mappers.getMapper( Issue1339Mapper.class );

    class Source {
        //CHECKSTYLE:OFF
        public String field;
        //CHECKSTYLE:ON
    }

    class Target {
        //CHECKSTYLE:OFF
        public String field;
        public int otherField;
        //CHECKSTYLE:ON
    }

    @Mapping(target = "otherField", ignore = true)
    Target map(Source source, int primitive1, @Context int primitive2);
}
