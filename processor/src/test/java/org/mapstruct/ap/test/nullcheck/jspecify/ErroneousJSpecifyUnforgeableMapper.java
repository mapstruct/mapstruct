/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Erroneous mapper: the nullable source property cannot be mapped to the target constructor
 * parameter's type at all (String -> AddressBean, with no mapping method declared). The
 * "cannot find mapping" error must be the only diagnostic; the JSpecify
 * "@NonNull constructor parameter" error must not also fire when assignment resolution failed.
 */
@Mapper
public interface ErroneousJSpecifyUnforgeableMapper {

    @Mapping(target = "payload", source = "nullableValue")
    UnmappableConstructorTargetBean map(SourceBean source);
}
