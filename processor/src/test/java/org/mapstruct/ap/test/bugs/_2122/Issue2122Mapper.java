/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * @author Christian Bandowski
 */
@Mapper
public interface Issue2122Mapper {

    Issue2122Mapper INSTANCE = Mappers.getMapper( Issue2122Mapper.class );

    @Mapping(target = "embeddedTarget", source = "value")
    Target toTarget(Source source);

    EmbeddedTarget toEmbeddedTarget(String value);

    default <T> List<T> singleEntry(T entry) {
        return Collections.singletonList( entry );
    }

}
