/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.abstractGenericTarget;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ParentMapper {

    ParentMapper INSTANCE = Mappers.getMapper( ParentMapper.class );

    ImmutableParent toImmutable(ParentSource parentSource);

    MutableParent toMutable(ParentSource parentSource);

    /**
     * This method allows mapstruct to successfully write to {@link ImmutableParent#nonGenericChild}
     * by providing a concrete class to convert to.
     */
    ImmutableChild toChild(ChildSource child);

}
