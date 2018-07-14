/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks;

import java.util.List;
import java.util.Map;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * @author Andreas Gudian
 */
@Mapper( uses = ClassContainingCallbacks.class )
public abstract class SourceTargetCollectionMapper extends BaseMapper {
    public static final SourceTargetCollectionMapper INSTANCE = Mappers.getMapper( SourceTargetCollectionMapper.class );

    public abstract List<Target> sourceToTarget(List<Source> source);

    public abstract void sourceToTarget(List<Source> source, @MappingTarget List<Target> target);

    @IterableMapping(qualifiedBy = Qualified.class)
    public abstract List<Target> qualifiedSourceToTarget(List<Source> source);

    public abstract Map<String, Target> sourceToTarget(Map<String, Source> source);

    public abstract void sourceToTarget(Map<String, Source> source, @MappingTarget Map<String, Target> target);
}
