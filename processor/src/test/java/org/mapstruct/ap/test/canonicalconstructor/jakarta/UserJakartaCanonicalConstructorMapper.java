/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.jakarta;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.test.canonicalconstructor.defaultcomponentmodel.ExampleMapper;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressEntity;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA,
uses = ExampleMapper.class,
injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public abstract class UserJakartaCanonicalConstructorMapper {

    private final ContactRepository contactRepository;
    private final AddressMapper addressMapper;

    public UserJakartaCanonicalConstructorMapper(ContactRepository contactRepository, AddressMapper addressMapper) {
        this.contactRepository = contactRepository;
        this.addressMapper = addressMapper;
    }

    public UserDto map(UserEntity userEntity) {
        String phoneNumber = contactRepository.getUserPhoneNumber( userEntity.getUserId() );
        return map( userEntity, phoneNumber );
    }

    protected abstract UserDto map(UserEntity userEntity, String phoneNumber);

    protected AddressDto map(AddressEntity addressEntity) {
        return addressMapper.map( addressEntity );
    }

}
