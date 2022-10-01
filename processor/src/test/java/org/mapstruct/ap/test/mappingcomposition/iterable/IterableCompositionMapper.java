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

    @ToIterable
    List<String> prices(List<Integer> prices);

    @IterableMapping(numberFormat = "@#.00")
    @ToIterable
    List<String> duplicateAnnotation(List<Integer> prices);
}
