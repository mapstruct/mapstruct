/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
