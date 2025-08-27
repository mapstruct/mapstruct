/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.sealedinterface;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.SubclassMapping;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.WARN)
public interface Issue3836Mapper {

  Issue3836Mapper INSTANCE = Mappers.getMapper( Issue3836Mapper.class );

  @Mapping(source = "a", target = "to")
  To map(FromA from);

  @Mapping(source = "b", target = "to")
  To map(FromB fromB);

  @SubclassMapping(source = FromA.class, target = To.class)
  @SubclassMapping(source = FromB.class, target = To.class)
  To map(From from);
}