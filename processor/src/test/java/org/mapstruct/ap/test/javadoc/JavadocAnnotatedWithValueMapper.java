/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.javadoc;

import org.mapstruct.Javadoc;
import org.mapstruct.Mapper;

@Mapper
@Javadoc("This is the description\n"
        + "\n"
        + "@author author1\n"
        + "@author author2\n"
        + "\n"
        + "@deprecated Use {@link OtherMapper} instead\n"
        + "@since 0.1\n")
@Deprecated
public interface JavadocAnnotatedWithValueMapper {

}
