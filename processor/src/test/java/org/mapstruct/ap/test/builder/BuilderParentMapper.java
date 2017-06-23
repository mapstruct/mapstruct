package org.mapstruct.ap.test.builder;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR, uses = BuilderMapper.class)
public interface BuilderParentMapper {

    public ImmutableParent toImmutable(MutableParent source);
}
