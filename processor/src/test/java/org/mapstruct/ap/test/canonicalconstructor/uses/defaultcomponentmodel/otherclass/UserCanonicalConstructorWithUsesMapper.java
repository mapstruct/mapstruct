/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.uses.defaultcomponentmodel.otherclass;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressEntity;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;
import org.mapstruct.factory.Mappers;

@Mapper(uses = ExampleMapper.class)
public abstract class UserCanonicalConstructorWithUsesMapper {

    public static final UserCanonicalConstructorWithUsesMapper INSTANCE =
        Mappers.getMapper( UserCanonicalConstructorWithUsesMapper.class );

    private final ContactRepository contactRepository;
    private final AddressMapper addressMapper;

    public UserCanonicalConstructorWithUsesMapper(ContactRepository contactRepository, AddressMapper addressMapper) {
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
