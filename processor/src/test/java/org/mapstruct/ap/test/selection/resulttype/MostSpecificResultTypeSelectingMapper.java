/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface MostSpecificResultTypeSelectingMapper {

    MostSpecificResultTypeSelectingMapper INSTANCE = Mappers.getMapper( MostSpecificResultTypeSelectingMapper.class );

    @Mapping( target = "apple", source = "fruit")
    AppleFamily map(FruitFamily fruitFamily);

    default GoldenDelicious toGolden(IsFruit fruit) {
        return fruit != null ? new GoldenDelicious( fruit.getType() ) : null;
    }

    default Apple toApple(IsFruit fruit) {
        return fruit != null ? new Apple( fruit.getType() ) : null;
    }
}
