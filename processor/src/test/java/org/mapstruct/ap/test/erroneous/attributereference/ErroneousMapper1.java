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
public interface ErroneousMapper1 {

    @Mappings({
        @Mapping(target = "foo", source = "source.foobar")
    })
    Target sourceToTarget(Source source, DummySource source1);

}
