package org.mapstruct.ap.test.optionalmapping.nullcheckalways;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface OptionalNullCheckAlwaysMapper {

    OptionalNullCheckAlwaysMapper INSTANCE = Mappers.getMapper( OptionalNullCheckAlwaysMapper.class);

    Target toTarget(Source source);

}
