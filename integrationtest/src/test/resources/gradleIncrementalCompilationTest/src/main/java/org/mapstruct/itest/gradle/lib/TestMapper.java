package org.mapstruct.itest.gradle.lib;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import org.mapstruct.itest.gradle.model.Target;
import org.mapstruct.itest.gradle.model.Source;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TestMapper {
    @Mapping(source = "value", target = "field")
    public Target toTarget(Source source);
}
