/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.numbers;

import java.math.BigDecimal;
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

    @Mappings( {
        @Mapping( target = "i", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "ii", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "d", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "dd", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "f", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "ff", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "l", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "ll", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "b", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "bb", numberFormat = NUMBER_FORMAT, locale = "ru" ),
        @Mapping( target = "complex1", numberFormat = "##0.##E0", locale = "ru" ),
        @Mapping( target = "complex2", numberFormat = "$#.00", locale = "ru" ),
        @Mapping( target = "bigDecimal1", numberFormat = "#0.#E0", locale = "ru" ),
        @Mapping( target = "bigInteger1", numberFormat = "0.#############E0", locale = "ru" )

    } )
    Target sourceToTargetWithCustomLocale(Source source);

    @InheritInverseConfiguration( name = "sourceToTarget" )
    Source targetToSource(Target target);

    @InheritInverseConfiguration( name = "sourceToTargetWithCustomLocale" )
    Source targetToSourceWithCustomLocale(Target target);

    @IterableMapping( numberFormat = NUMBER_FORMAT )
    List<String> sourceToTarget(List<Float> source);

    @InheritInverseConfiguration( name = "sourceToTarget" )
    List<Float> targetToSource(List<String> source);

    @IterableMapping( numberFormat = "#0.#E0", locale = "fr" )
    List<String> sourceToTargetWithCustomLocale(List<BigDecimal> source);

    @InheritInverseConfiguration( name = "sourceToTargetWithCustomLocale" )
    List<BigDecimal> targetToSourceWithCustomLocale(List<String> source);

    @MapMapping( keyNumberFormat = NUMBER_FORMAT, valueNumberFormat = "##" )
    Map<String, String> sourceToTarget(Map<Float, Float> source);

    @MapMapping( keyNumberFormat = "#0.#E0", valueNumberFormat = "0.#############E0", locale = "fr" )
    Map<String, String> sourceToTargetWithCustomLocale(Map<BigDecimal, BigDecimal> source);

    @InheritInverseConfiguration( name = "sourceToTarget" )
    Map<Float, Float> targetToSource(Map<String, String> source);

    @InheritInverseConfiguration( name = "sourceToTargetWithCustomLocale" )
    Map<BigDecimal, BigDecimal> targetToSourceWithCustomLocale(Map<String, String> source);

}


