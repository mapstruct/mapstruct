package org.mapstruct.ap.test.references.usesPlain;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.references.ManualFooMapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = UsesPlainMapperConfig.class, usesPlain = ManualFooMapper.class)
public interface UsesPlainMapperConfigMapper2 {
    UsesPlainMapperConfigMapper2 INSTANCE = Mappers.getMapper( UsesPlainMapperConfigMapper2.class );

    Target toTarget(Source source);
}
