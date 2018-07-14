/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.selection.qualifier.bean.AbstractEntry;
import org.mapstruct.ap.test.selection.qualifier.bean.OriginalRelease;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousMovieFactoryMapper {

    ErroneousMovieFactoryMapper INSTANCE = Mappers.getMapper( ErroneousMovieFactoryMapper.class );

    @BeanMapping
    AbstractEntry toGerman( OriginalRelease movies );

}
