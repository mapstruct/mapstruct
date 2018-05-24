package org.mapstruct.ap.test.references.usesPlain;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.references.FooMapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = UsesPlainMapperConfig.class, uses = FooMapper.class)
public interface UsesPlainMapperConfigMapper {
    UsesPlainMapperConfigMapper INSTANCE = Mappers.getMapper( UsesPlainMapperConfigMapper.class );

    Target toTarget(Source source);
}
