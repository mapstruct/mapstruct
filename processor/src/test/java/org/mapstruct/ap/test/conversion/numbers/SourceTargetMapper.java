/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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

    @Mapping( target = "i", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "ii", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "d", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "dd", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "f", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "ff", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "l", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "ll", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "b", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "bb", numberFormat = NUMBER_FORMAT )
    @Mapping( target = "complex1", numberFormat = "##0.##E0" )
    @Mapping( target = "complex2", numberFormat = "$#.00" )
    @Mapping( target = "bigDecimal1", numberFormat = "#0.#E0" )
    @Mapping( target = "bigInteger1", numberFormat = "0.#############E0" )
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


