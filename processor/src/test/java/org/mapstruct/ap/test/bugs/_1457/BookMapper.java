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
public abstract class BookMapper {

    public static final BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    public abstract TargetBook mapBook(SourceBook sourceBook, String authorFirstName, String authorLastName);

    @AfterMapping
    protected void fillAuthor(@MappingTarget TargetBook targetBook, String authorFirstName, String authorLastName) {
        targetBook.setAuthorFirstName( authorFirstName );
        targetBook.setAuthorLastName( authorLastName );
    }

}
