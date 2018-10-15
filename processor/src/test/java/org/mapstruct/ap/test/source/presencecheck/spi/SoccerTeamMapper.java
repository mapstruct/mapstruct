/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED )
public interface SoccerTeamMapper {

    SoccerTeamMapper INSTANCE = Mappers.getMapper( SoccerTeamMapper.class );

    @Mapping( target = "goalKeeperName", ignore = true )
    SoccerTeamTarget mapAdder( SoccerTeamSource in );

    @Mappings({
        @Mapping(target = "players", ignore = true),
        @Mapping(target = "goalKeeperName", source = "goalKeeper.name")
    })
    SoccerTeamTarget mapNested( SoccerTeamSource in );

}
