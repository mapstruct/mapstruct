/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

import java.util.stream.Stream;

/**
 * @author orange add
 * @date 2022/8/27 22:56
 */
@Mapper
public interface AnnotateStreamMappingMethodMapper {

    @AnnotateWith(CustomMethodOnlyAnnotation.class)
    Stream<String> toStringStream(Stream<Integer> integerStream);
}
