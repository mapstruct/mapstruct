package org.mapstruct.ap.test.optionalmapping.nested;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OptionalNestedMapper {

    OptionalNestedMapper INSTANCE = Mappers.getMapper(OptionalNestedMapper.class);

    @Mapping( source = "optionalToNonOptional?.value?", target = "optionalToNonOptional")
    @Mapping( source = "optionalToOptional?.value", target = "optionalToOptional")
    @Mapping( source = "nonOptionalToNonOptional?.value", target = "nonOptionalToNonOptional")
    @Mapping( source = "nonOptionalToOptional?.value", target = "nonOptionalToOptional")
    Target toTarget(Source source);

}
