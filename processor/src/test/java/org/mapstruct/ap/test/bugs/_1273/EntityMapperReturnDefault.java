/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1273;

import java.util.ArrayList;

import org.mapstruct.Mapper;
import org.mapstruct.ObjectFactory;

@Mapper
public interface EntityMapperReturnDefault {

    Dto asTarget(Entity entity);

    @ObjectFactory
    default Dto createDto() {
        Dto result = new Dto();
        result.setLongs( new ArrayList<>(  ) );
        return result;
    }
}
