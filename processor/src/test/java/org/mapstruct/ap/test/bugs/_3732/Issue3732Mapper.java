package org.mapstruct.ap.test.bugs._3732;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3732Mapper {

    Issue3732Mapper INSTANCE = Mappers.getMapper( Issue3732Mapper.class );

    ModelB map(ModelA model);
}
