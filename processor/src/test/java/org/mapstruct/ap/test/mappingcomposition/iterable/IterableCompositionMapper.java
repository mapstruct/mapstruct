/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcomposition.iterable;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

/**
 * @author orange add
 */
@Mapper
public interface IterableCompositionMapper {

    @CustomIterableAnnotation
    List<String> prices(List<Integer> prices);

    @IterableMapping(numberFormat = "@#")
    @CustomIterableAnnotation
    List<String> duplicateAnnotation(List<Integer> prices);
}
