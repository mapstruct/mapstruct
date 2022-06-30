/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.qualifier.named;

import java.util.List;
import java.util.Map;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.selection.qualifier.handwritten.SomeOtherMapper;
import org.mapstruct.factory.Mappers;

import com.google.common.collect.ImmutableMap;
import org.mapstruct.Named;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = { SomeOtherMapper.class } )
public abstract class KeyWordMapper {

    private static final Map<String, String> EN_GER = ImmutableMap.<String, String>builder()
            .put( "magnificent", "Großartig" )
            .put( "evergreen", "Evergreen" )
            .put( "classic", "Klassiker" )
            .put( "box office flop", "Kasse Flop" )
            .build();

    public static final KeyWordMapper INSTANCE = Mappers.getMapper( KeyWordMapper.class );

    @IterableMapping( dateFormat = "", qualifiedByName = "EnglishToGerman" )
    abstract List<String> mapKeyWords( List<String> keyWords );

    @Named( "EnglishToGerman" )
    public String mapKeyWord( String keyword ) {
        return EN_GER.get( keyword );
    }
}
