/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.test.source.nullvaluecheckstrategy;

import static org.mapstruct.NullValueCheckStrategy.ON_IMPLICIT_CONVERSION;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( config = RockFestivalMapperConfig.class, nullValueCheckStrategy = ON_IMPLICIT_CONVERSION )
public abstract class RockFestivalMapperOveridingConfig {

    public static final RockFestivalMapperOveridingConfig INSTANCE =
        Mappers.getMapper( RockFestivalMapperOveridingConfig.class );

    @Mapping( target = "stage", source = "artistName" )
    public abstract RockFestivalTarget map( RockFestivalSource in );

    public Stage artistToStage( String name ) {
        return Stage.forArtist( name );
    }
}
