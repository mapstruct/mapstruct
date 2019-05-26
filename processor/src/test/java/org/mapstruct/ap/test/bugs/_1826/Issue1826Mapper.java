/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1826;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue1826Mapper {

  Issue1826Mapper INSTANCE = Mappers.getMapper( Issue1826Mapper.class );

  @Mapping(target = "content", source = "sourceChild.content")
  Target sourceAToTarget(SourceParent sourceParent);

}
