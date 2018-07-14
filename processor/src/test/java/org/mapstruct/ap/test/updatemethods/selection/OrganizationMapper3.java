/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.selection;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ap.test.updatemethods.BossDto;
import org.mapstruct.ap.test.updatemethods.BossEntity;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper(uses = { ExternalMapper.class })
public interface OrganizationMapper3 {

    OrganizationMapper3 INSTANCE = Mappers.getMapper( OrganizationMapper3.class );

    void toBossEntity(BossDto dto, @MappingTarget BossEntity entity);

}
