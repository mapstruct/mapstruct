/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.nullvaluecheckstrategy;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( config = RockFestivalMapperConfig.class )
public abstract class RockFestivalMapperWithConfig {

    public static final RockFestivalMapperWithConfig INSTANCE =
        Mappers.getMapper( RockFestivalMapperWithConfig.class );

    @Mapping( target = "stage", source = "artistName" )
    public abstract RockFestivalTarget map( RockFestivalSource in );

    public Stage artistToStage( String name ) {
        return Stage.forArtist( name );
    }
}
