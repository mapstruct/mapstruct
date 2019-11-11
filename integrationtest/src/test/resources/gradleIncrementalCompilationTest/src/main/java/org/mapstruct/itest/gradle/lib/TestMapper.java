package org.mapstruct.itest.gradle.lib;

import org.mapstruct.Mapper;

import org.mapstruct.itest.gradle.model.Target;
import org.mapstruct.itest.gradle.model.Source;

@Mapper
public interface TestMapper {
    public Target toTarget(Source source);
}
