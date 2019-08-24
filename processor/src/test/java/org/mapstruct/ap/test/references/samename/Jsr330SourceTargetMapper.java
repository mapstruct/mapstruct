/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.references.samename;

import org.mapstruct.ComponentModel;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.references.samename.a.CustomMapper;
import org.mapstruct.ap.test.references.samename.model.Source;
import org.mapstruct.ap.test.references.samename.model.Target;
import org.mapstruct.factory.Mappers;

/**
 * @author Gunnar Morling
 */
@Mapper(
    componentModel = ComponentModel.JSR330,
    uses = {
        CustomMapper.class,
        org.mapstruct.ap.test.references.samename.b.CustomMapper.class
    })
public interface Jsr330SourceTargetMapper {

    Jsr330SourceTargetMapper INSTANCE = Mappers.getMapper( Jsr330SourceTargetMapper.class );

    Target sourceToTarget(Source source);
}
