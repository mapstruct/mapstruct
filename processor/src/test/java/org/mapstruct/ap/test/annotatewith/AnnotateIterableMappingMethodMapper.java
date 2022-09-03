/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author orange add
 */
@Mapper
public interface AnnotateIterableMappingMethodMapper {

    @AnnotateWith(CustomMethodOnlyAnnotation.class)
    List<String> toStringList(List<Integer> integers);
}
