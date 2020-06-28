/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2122;

import java.util.Collections;
import java.util.HashMap;
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
    @Mapping(target = "embeddedMapTarget", source = "value")
    @Mapping(target = "embeddedListListTarget", source = "value")
    Target toTarget(Source source);

    EmbeddedTarget toEmbeddedTarget(String value);

    default <T> List<T> singleEntry(T entry) {
        return Collections.singletonList( entry );
    }

    default <T> List<List<T>>  singleNestedListEntry(T entry) {
        return Collections.singletonList( Collections.singletonList( entry ) );
    }

    default <T> HashMap<String, T> singleEntryMap(T entry) {
        HashMap<String, T> result = new HashMap<>(  );
        result.put( "test", entry );
        return result;
    }

}
