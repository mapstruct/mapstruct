/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

@Mapper
@Deprecated( since = "1.8" )
@AnnotateWith(
    value = Deprecated.class,
    elements = {
        @AnnotateWith.Element( name = "forRemoval", booleans = false),
        @AnnotateWith.Element( name = "since", strings = "1.5")
    }
)
public class RepeatDeprecatedMapperWithParams {
}
