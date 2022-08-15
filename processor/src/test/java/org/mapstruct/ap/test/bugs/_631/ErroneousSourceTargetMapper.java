/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._631;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 * @param <X>
 * @param <Y>
 */
@Mapper
public interface ErroneousSourceTargetMapper<X extends Base1, Y extends Base2> {

    ErroneousSourceTargetMapper INSTANCE = Mappers.getMapper( ErroneousSourceTargetMapper.class );

    X mapIntegerToBase1(Integer obj);

    String mapBase2ToInteger(Y obj);
}
