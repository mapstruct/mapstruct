/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.selection.qualifier.bean.GermanRelease;
import org.mapstruct.ap.test.selection.qualifier.bean.OriginalRelease;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Facts;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Reverse;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = { Facts.class, Reverse.class } )
public interface MapperWithoutQualifiedBy {

    MapperWithoutQualifiedBy INSTANCE = Mappers.getMapper( MapperWithoutQualifiedBy.class );

    @Mapping( target = "title", source = "title" )
    @Mapping( target = "keyWords", ignore = true )
    @Mapping( target = "facts", ignore = true )
    GermanRelease map( OriginalRelease movies );

}
