/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.presencecheck.spi;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author Cindy Wang
 */
@Mapper
public interface SoccerTeamMapperNestedObjects {
    SoccerTeamMapperNestedObjects INSTANCE = Mappers.getMapper( SoccerTeamMapperNestedObjects.class );

    @Mapping(target = "players", ignore = true)
    @Mapping(target = "goalKeeperName", source = "goalKeeper.name")
    @Mapping(target = "referee.name", source = "refereeName")
    SoccerTeamTargetWithPresenceCheck mapNested( SoccerTeamSource in );

}
