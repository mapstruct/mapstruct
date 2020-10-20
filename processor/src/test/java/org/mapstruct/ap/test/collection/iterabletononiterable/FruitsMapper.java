/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.iterabletononiterable;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Saheb Preet Singh
 */
@Mapper
public interface FruitsMapper {

    FruitsMapper INSTANCE = Mappers.getMapper(
        FruitsMapper.class );

    FruitsMenu fruitSaladToMenu(FruitSalad salad);

    FruitSalad fruitsMenuToSalad(FruitsMenu menu);
}
