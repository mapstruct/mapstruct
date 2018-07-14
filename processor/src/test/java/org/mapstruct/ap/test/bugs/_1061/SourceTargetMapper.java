/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1061;

import java.util.List;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(implementationPackage = "")
public interface SourceTargetMapper {

    List<Integer> map(List<String> strings);
}
