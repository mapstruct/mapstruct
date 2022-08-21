/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2907.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2907.Source;
import org.mapstruct.ap.test.bugs._2907.Target;

@Mapper
public interface Issue2907Mapper {

    Target map(Source source);

}
