/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1685;

import org.mapstruct.BeanMapping;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper( collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED )
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    @Mappings({
        @Mapping(target = "contactDataDTO.email", source = "email" ),
        @Mapping(target = "contactDataDTO.phone", source = "phone"),
        @Mapping(target = "contactDataDTO.address", source = "address"),
        @Mapping(target = "contactDataDTO.preferences", source = "preferences"),
        @Mapping(target = "contactDataDTO.settings", source = "settings")
    })
    UserDTO userToUserDTO(User user);

    @InheritInverseConfiguration
    void updateUserFromUserDTO(UserDTO userDTO, @MappingTarget User user);

    @InheritInverseConfiguration
    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    void updateUserFromUserAndIgnoreDTO(UserDTO userDTO, @MappingTarget User user);

    @InheritInverseConfiguration
    @BeanMapping( nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_DEFAULT )
    void updateUserFromUserAndDefaultDTO(UserDTO userDTO, @MappingTarget User user);

}
