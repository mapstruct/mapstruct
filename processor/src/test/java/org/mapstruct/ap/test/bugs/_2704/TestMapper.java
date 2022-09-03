/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2704;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ap.test.bugs._2704.TopLevel.Target;

/**
 * @author Valentin Kulesh
 */
@Mapper(implementationPackage = "")
public interface TestMapper {
    @Mapping(target = "e", constant = "VALUE1")
    Target test(Object unused);
}
