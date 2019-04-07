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
public abstract class BookMapper {

    public static final BookMapper INSTANCE = Mappers.getMapper( BookMapper.class );

    public abstract TargetBook mapBook(SourceBook sourceBook, String authorFirstName, String authorLastName);

    @AfterMapping
    protected void fillAuthor(@MappingTarget TargetBook targetBook, String authorFirstName, String authorLastName) {
        targetBook.setAuthorFirstName( authorFirstName );
        targetBook.setAuthorLastName( authorLastName );
    }

    @AfterMapping
    protected void withoutAuthorNames(@MappingTarget TargetBook targetBook) {
        targetBook.setAfterMappingWithoutAuthorName( true );
    }

    @AfterMapping
    protected void withOnlyFirstName(@MappingTarget TargetBook targetBook, String authorFirstName) {
        targetBook.setAfterMappingWithOnlyFirstName( authorFirstName );
    }

    @AfterMapping
    protected void withOnlyLastName(@MappingTarget TargetBook targetBook, String authorLastName) {
        targetBook.setAfterMappingWithOnlyLastName( authorLastName );
    }

    @AfterMapping
    protected void withDifferentVariableName(@MappingTarget TargetBook targetBook, String author) {
        targetBook.setAfterMappingWithDifferentVariableName( true );
    }

}
