/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.named;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.selection.qualifier.bean.AbstractEntry;
import org.mapstruct.ap.test.selection.qualifier.bean.OriginalRelease;
import org.mapstruct.ap.test.selection.qualifier.bean.ReleaseFactory;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = ReleaseFactory.class )
public interface MovieFactoryMapper {

    MovieFactoryMapper INSTANCE = Mappers.getMapper( MovieFactoryMapper.class );

    @BeanMapping(qualifiedByName = "CreateGermanRelease" )
    AbstractEntry toGerman( OriginalRelease movies );

}
