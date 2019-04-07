/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1457;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ObjectFactoryBookMapper {

    public static final ObjectFactoryBookMapper INSTANCE = Mappers.getMapper( ObjectFactoryBookMapper.class );

    public abstract TargetBook mapBook(SourceBook sourceBook, String authorFirstName, String authorLastName);

    @ObjectFactory
    protected TargetBook createTargetBook(String authorFirstName, String authorLastName) {
        TargetBook targetBook = new TargetBook();
        targetBook.setAuthorFirstName( authorFirstName );
        targetBook.setAuthorLastName( authorLastName );

        return targetBook;
    }

}
