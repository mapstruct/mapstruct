/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.nested;

import java.util.Locale;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SourceAnnotation;
import org.mapstruct.ap.test.sourceannotation.StringConversion;
import org.mapstruct.ap.test.sourceannotation.Target;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {

    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );

    @Mapping(target = "firstProperty", source = "alpha.firstProperty")
    @Mapping(target = "secondProperty", source = "bravo.secondProperty")
    Target sourceToTarget(SourceContainer source);

    default String mapProperty(String source, @SourceAnnotation StringConversion conversion) {
        if ( conversion == null ) {
            return source;
        }

        String conversionType = conversion.value();

        switch ( conversionType ) {
            case "toLowerCase":
                return source.toLowerCase( Locale.ROOT );
            case "toUpperCase":
                return source.toUpperCase( Locale.ROOT );
            default:
                return source;
        }
    }

}
