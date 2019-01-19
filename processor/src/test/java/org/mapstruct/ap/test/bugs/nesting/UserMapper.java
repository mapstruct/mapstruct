package org.mapstruct.ap.test.bugs.nesting;

import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(  )
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mappings({
        @Mapping(source="email", target="contactDataDTO.email"),
        @Mapping(source="phone", target="contactDataDTO.phone"),
        @Mapping(source="address", target="contactDataDTO.address")
    })
    UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    void updateUserFromUserDTO(UserDTO userDTO, @MappingTarget  User user);
}