/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2195;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.bugs._2195.dto.Source;
import org.mapstruct.ap.test.bugs._2195.dto.Target;
import org.mapstruct.ap.test.bugs._2195.dto.TargetBase;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2195Mapper {

    Issue2195Mapper INSTANCE = Mappers.getMapper( Issue2195Mapper.class );

    @BeanMapping( resultType = Target.class )
    TargetBase map(Source source);

}
