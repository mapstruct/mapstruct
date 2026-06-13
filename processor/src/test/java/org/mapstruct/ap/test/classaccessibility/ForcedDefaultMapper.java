/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.classaccessibility;

import org.mapstruct.ClassAccessibility;
import org.mapstruct.Mapper;

@Mapper(accessibility = ClassAccessibility.DEFAULT)
public interface ForcedDefaultMapper {
    Target map(Source value);
}

