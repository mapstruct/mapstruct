/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper( uses = ClassContainingCallbacks.class )
public abstract class SourceTargetMapper extends BaseMapper {
    public static final SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    public abstract void sourceToTarget(Source source, @MappingTarget Target target);

    @BeanMapping(qualifiedBy = Qualified.class)
    public abstract Target qualifiedSourceToTarget(Source source);

    public abstract TargetEnum toTargetEnum(SourceEnum sourceEnum);
}
