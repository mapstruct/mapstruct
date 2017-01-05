/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.conversion.numbers;

import java.util.List;
import java.util.Map;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.IterableMapping;
import org.mapstruct.MapMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    String NUMBER_FORMAT = "##.00";

    @Mappings( {
            @Mapping( target = "i", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "ii", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "d", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "dd", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "f", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "ff", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "l", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "ll", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "b", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "bb", numberFormat = NUMBER_FORMAT ),
            @Mapping( target = "complex1", numberFormat = "##0.##E0" ),
            @Mapping( target = "complex2", numberFormat = "$#.00" ),
            @Mapping( target = "bigDecimal1", numberFormat = "#0.#E0" ),
            @Mapping( target = "bigInteger1", numberFormat = "0.#############E0" )

    } )
    Target sourceToTarget(Source source);

    @InheritInverseConfiguration
    Source targetToSource(Target target);

    @IterableMapping( numberFormat = NUMBER_FORMAT )
    List<String> sourceToTarget(List<Float> source);

    @InheritInverseConfiguration
    List<Float> targetToSource(List<String> source);

    @MapMapping( keyNumberFormat = NUMBER_FORMAT, valueNumberFormat = "##" )
    Map<String, String> sourceToTarget(Map<Float, Float> source);

    @InheritInverseConfiguration
    Map<Float, Float> targetToSource(Map<String, String> source);

}


