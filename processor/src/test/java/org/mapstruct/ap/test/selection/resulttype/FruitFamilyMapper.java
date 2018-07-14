/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import java.util.List;
import java.util.Map;

import org.mapstruct.BeanMapping;
import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = AppleFactory.class)
public interface FruitFamilyMapper {

    FruitFamilyMapper INSTANCE = Mappers.getMapper( FruitFamilyMapper.class );

    @Mapping(target = "apple", resultType = GoldenDelicious.class)
    AppleFamily map(AppleFamilyDto source);

    @IterableMapping( elementTargetType = GoldenDelicious.class )
    List<Apple> mapToGoldenDeliciousList(List<AppleDto> source);

    @MapMapping( keyTargetType = GoldenDelicious.class, valueTargetType = Apple.class )
    Map<Apple, Apple> mapToGoldenDeliciousMap(Map<AppleDto, AppleDto> source);

    GoldenDelicious mapToGoldenDelicious(AppleDto source);

    @BeanMapping(resultType = Apple.class)
    Apple mapToApple(AppleDto source);

}
