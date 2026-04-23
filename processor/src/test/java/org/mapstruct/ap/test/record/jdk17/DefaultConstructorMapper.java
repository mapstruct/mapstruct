/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.record.jdk17;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Oliver Erhart
 */
@Mapper
public interface DefaultConstructorMapper {

    DefaultConstructorMapper INSTANCE = Mappers.getMapper( DefaultConstructorMapper.class );

    TaskDto toRecord(Task source);

    record TaskDto(String id, Long number) {

        @Default
        TaskDto(String id) {
            this( id, 1L );
        }

    }

    record Task(String id, Long number) {

    }

    @Documented
    @Target(ElementType.CONSTRUCTOR)
    @Retention(RetentionPolicy.SOURCE)
    @interface Default {
    }
}
