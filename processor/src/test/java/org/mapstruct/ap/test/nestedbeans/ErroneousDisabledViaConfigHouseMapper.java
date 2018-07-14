/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper(config = DisableConfig.class)
public interface ErroneousDisabledViaConfigHouseMapper {

    HouseDto houseToHouseDto(House house);
}
