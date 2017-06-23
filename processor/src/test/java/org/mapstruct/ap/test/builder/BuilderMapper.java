package org.mapstruct.ap.test.builder;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface BuilderMapper {

    @Mapping(target = "pops", source = "mutableParent")
    public ImmutableTarget toImmutable(MutableSource source);
}
