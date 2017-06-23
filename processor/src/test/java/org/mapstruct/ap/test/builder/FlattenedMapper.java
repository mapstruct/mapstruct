package org.mapstruct.ap.test.builder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface FlattenedMapper {

    @Mappings({
            @Mapping(target = "first.age", source = "count"),
            @Mapping(target = "second", ignore = true)
    })
    public ImmutableFlattened toFlattened(MutableParent parent);
}
