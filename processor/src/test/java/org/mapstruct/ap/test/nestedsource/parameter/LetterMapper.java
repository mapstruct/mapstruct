/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsource.parameter;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface LetterMapper {

    LetterMapper INSTANCE = Mappers.getMapper( LetterMapper.class );

    @Mappings( {
        @Mapping( target = "fontType", source = "font.type"),
        @Mapping( target = "fontSize", source = "font.size"),
        @Mapping( target = "letterHeading", source = "heading"),
        @Mapping( target = "letterBody", source = "body"),
        @Mapping( target = "letterSignature", source = "dto.signature")
    } )
    LetterEntity normalize(LetterDto dto);

    @InheritInverseConfiguration
    @Mapping(target = "font", source = "entity")
    LetterDto deNormalizeLetter(LetterEntity entity);

    @Mappings( {
        @Mapping( target = "type", source = "fontType"),
        @Mapping( target = "size", source = "fontSize")
    } )
    FontDto deNormalizeFont(LetterEntity entity);

}
