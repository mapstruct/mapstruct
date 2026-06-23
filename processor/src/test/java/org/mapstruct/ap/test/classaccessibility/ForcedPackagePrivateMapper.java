/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.classaccessibility;

import org.mapstruct.ClassAccessibility;
import org.mapstruct.Mapper;

@Mapper(accessibility = ClassAccessibility.PACKAGE_PRIVATE)
public interface ForcedPackagePrivateMapper {
    Target map(Source value);
}

