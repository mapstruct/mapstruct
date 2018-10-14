/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.hybrid;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;
import org.mapstruct.ap.test.selection.qualifier.handwritten.SomeOtherMapper;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Titles;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = { Titles.class, SomeOtherMapper.class } )
public interface ReleaseMapper {

    ReleaseMapper INSTANCE = Mappers.getMapper( ReleaseMapper.class );

    @Mapping( target = "title", qualifiedBy = { TitleTranslator.class }, qualifiedByName = { "EnglishToGerman" } )
    TargetRelease toGerman( SourceRelease movies );
}
