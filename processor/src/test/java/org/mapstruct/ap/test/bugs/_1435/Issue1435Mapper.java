/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1435;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(config = Config.class)
public interface Issue1435Mapper {

    Issue1435Mapper INSTANCE = Mappers.getMapper( Issue1435Mapper.class );

    OutObject map(InObject source);
}
