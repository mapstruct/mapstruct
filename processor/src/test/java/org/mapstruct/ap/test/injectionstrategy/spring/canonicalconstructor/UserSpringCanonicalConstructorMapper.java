package org.mapstruct.ap.test.injectionstrategy.spring.canonicalconstructor;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class UserSpringCanonicalConstructorMapper {

    private final ContactRepository contactRepository;

    public UserSpringCanonicalConstructorMapper(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public UserDto map(UserEntity userEntity) {
        String phoneNumber = contactRepository.getUserPhoneNumber( userEntity.getUserId() );
        return map( userEntity, phoneNumber );
    }

    protected abstract UserDto map(UserEntity userEntity, String phoneNumber);

}
