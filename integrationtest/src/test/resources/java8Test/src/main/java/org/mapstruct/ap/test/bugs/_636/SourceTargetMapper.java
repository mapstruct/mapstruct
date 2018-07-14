/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._636;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper extends SourceTargetBaseMapper {
    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mappings({
        @Mapping(source = "idFoo", target = "foo"),
        @Mapping(source = "idBar", target = "bar")
    })
    Target mapSourceToTarget(Source source);

    default Foo fooFromId(long id) {
        return new Foo(id);
    }

    static Bar barFromId(String id) {
        return new Bar(id);
    }

}
