/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
