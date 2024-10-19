package org.mapstruct.ap.test.bugs._3707;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue3707Mapper {

    Issue3707Mapper INSTANCE = Mappers.getMapper( Issue3707Mapper.class );

    StringDto clone(StringDto stringDto);

    BigIntegerDto clone(BigIntegerDto bigIntegerDto);
}
