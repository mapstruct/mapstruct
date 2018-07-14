/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1425;

import java.time.LocalDate;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Bandowski
 */
@Mapper
public abstract class Issue1425Mapper {

    public static final Issue1425Mapper INSTANCE = Mappers.getMapper( Issue1425Mapper.class );

    public abstract Target map(Source source);

    LocalDate now() {
        return LocalDate.now();
    }
}
