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

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ap.test.selection.qualifier.annotation.EnglishToGerman;
import org.mapstruct.ap.test.selection.qualifier.annotation.TitleTranslator;
import org.mapstruct.ap.test.selection.qualifier.bean.GermanRelease;
import org.mapstruct.ap.test.selection.qualifier.bean.OriginalRelease;
import org.mapstruct.ap.test.selection.qualifier.handwritten.SomeOtherMapper;
import org.mapstruct.ap.test.selection.qualifier.handwritten.Titles;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( uses = { Titles.class, SomeOtherMapper.class, KeyWordMapper.class, FactMapper.class } )
public interface MovieMapper {

    MovieMapper INSTANCE = Mappers.getMapper( MovieMapper.class );

    @Mappings( {
        @Mapping( target = "title", qualifiedBy = { TitleTranslator.class, EnglishToGerman.class } )
    } )
    GermanRelease toGerman( OriginalRelease movies );

}
