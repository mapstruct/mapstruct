/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.attributereference;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ErroneousMapper {

    @Mappings({
        @Mapping(target = "foo", source = "bar"),
        @Mapping(target = "foo", source = "source1.foo" ),
        @Mapping(target = "bar", source = "foo")
    })
    Target sourceToTarget(Source source);

    // to test that nested is also reported correctly
    @Mapping(target = "foo", source = "source1.foo" )
    Target sourceToTarget2(Source source);

    AnotherTarget sourceToAnotherTarget(Source source);
}
