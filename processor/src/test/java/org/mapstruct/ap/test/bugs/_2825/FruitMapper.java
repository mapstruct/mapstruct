package org.mapstruct.ap.test.bugs._2825;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassExhaustiveStrategy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper(subclassExhaustiveStrategy = SubclassExhaustiveStrategy.RUNTIME_EXCEPTION)

public interface FruitMapper {

    FruitMapper INSTANCE = Mappers.getMapper(FruitMapper.class);



    @SubclassMapping(target = AppleDto.class,source = Apple.class)
    @SubclassMapping(target = OrangeDto.class,source = Orange.class)
    FruitDto map(Fruit fruit);
}
