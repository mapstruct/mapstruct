/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.nested;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.imports.nested.other.TargetInOtherPackage;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface TargetInOtherPackageMapper {

    TargetInOtherPackage map(Source source);
}
