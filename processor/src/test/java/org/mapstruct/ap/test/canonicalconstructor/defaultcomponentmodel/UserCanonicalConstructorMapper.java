/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.defaultcomponentmodel;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressEntity;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserCanonicalConstructorMapper {

    public static final UserCanonicalConstructorMapper INSTANCE =
        Mappers.getMapper( UserCanonicalConstructorMapper.class );

    private final ContactRepository contactRepository;
    private final AddressMapper addressMapper;

    public UserCanonicalConstructorMapper(ContactRepository contactRepository, AddressMapper addressMapper) {
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
