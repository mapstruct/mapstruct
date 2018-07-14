/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.misbalancedbraces;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface MapperWithMalformedExpression {

    @Mapping(target = "foo", expression = "java( Boolean.valueOf( source.foo > 0 ) ) )")
    Target sourceToTarget(Source source);
}
