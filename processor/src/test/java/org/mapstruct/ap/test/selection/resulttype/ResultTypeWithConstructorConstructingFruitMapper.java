/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface ResultTypeWithConstructorConstructingFruitMapper {

    ResultTypeWithConstructorConstructingFruitMapper INSTANCE = Mappers.getMapper(
        ResultTypeWithConstructorConstructingFruitMapper.class );

    @BeanMapping(resultType = Citrus.class)
    Fruit map(FruitDto source);

}
