/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.selection.qualifier;

import com.google.common.collect.ImmutableMap;
import java.util.List;
import java.util.Map;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;
import org.mapstruct.ap.test.selection.qualifier.handwritten.SomeOtherMapper;
import org.mapstruct.factory.Mappers;

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

    @IterableMapping( dateFormat = "", qualifiedBy = { EnglishToGerman.class } )
    abstract List<String> mapKeyWords( List<String> keyWords );

    @EnglishToGerman
    public String mapKeyWord( String keyword ) {
        return EN_GER.get( keyword );
    }
}
