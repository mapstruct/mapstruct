/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousResultTypeNoEmptyConstructorMapper {

    @BeanMapping(resultType = Banana.class)
    @Mapping(target = "type", ignore = true)
    Fruit map(FruitDto source);
}
