/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1457;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class ErroneousBookMapper {

    public static final ErroneousBookMapper INSTANCE = Mappers.getMapper( ErroneousBookMapper.class );

    public abstract TargetBook mapBook(SourceBook sourceBook, String authorFirstName, String authorLastName);

    @AfterMapping
    protected void fillAuthor(@MappingTarget TargetBook targetBook, String authorFirstN, String authorLastN) {
        targetBook.setAuthorFirstName( authorFirstN );
        targetBook.setAuthorLastName( authorLastN );
    }

}
