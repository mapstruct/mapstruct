/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 *
 */
@Mapper
public abstract class SourceTargetMapper {

    public static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    public abstract Source toSource(TargetTo target);

    public abstract TargetTo toTarget(Source source);
}
