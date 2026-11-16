/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.javadoc;

import org.mapstruct.Javadoc;
import org.mapstruct.Mapper;

@Mapper
@Javadoc(
        value = "This is the description",
        authors = { "author1", "author2" },
        deprecated = "Use {@link OtherMapper} instead",
        since = "0.1"
)
@Deprecated
public interface JavadocAnnotatedWithAttributesMapper {

}
