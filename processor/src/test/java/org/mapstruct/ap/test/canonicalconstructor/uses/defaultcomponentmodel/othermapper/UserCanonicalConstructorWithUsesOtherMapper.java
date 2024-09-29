/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.uses.defaultcomponentmodel.othermapper;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;
import org.mapstruct.factory.Mappers;

@Mapper(uses = AddressMapper.class)
public abstract class UserCanonicalConstructorWithUsesOtherMapper {

    public static final UserCanonicalConstructorWithUsesOtherMapper INSTANCE =
        Mappers.getMapper( UserCanonicalConstructorWithUsesOtherMapper.class );

    private final ContactRepository contactRepository;

    public UserCanonicalConstructorWithUsesOtherMapper(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public UserDto map(UserEntity userEntity) {
        String phoneNumber = contactRepository.getUserPhoneNumber( userEntity.getUserId() );
        return map( userEntity, phoneNumber );
    }

    protected abstract UserDto map(UserEntity userEntity, String phoneNumber);

}
