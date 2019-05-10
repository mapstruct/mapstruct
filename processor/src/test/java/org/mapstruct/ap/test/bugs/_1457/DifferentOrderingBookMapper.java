/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1457;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class DifferentOrderingBookMapper {

    public static final DifferentOrderingBookMapper INSTANCE = Mappers.getMapper( DifferentOrderingBookMapper.class );

    public abstract TargetBook mapBook(SourceBook sourceBook, String authorFirstName, String authorLastName);

    @AfterMapping
    protected void fillAuthor(String authorLastName, String authorFirstName, @MappingTarget TargetBook targetBook) {
        targetBook.setAuthorLastName( authorLastName );
        targetBook.setAuthorFirstName( authorFirstName );
    }

}
