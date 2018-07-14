/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.resulttype;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousFruitMapper2 {

    ErroneousFruitMapper2 INSTANCE = Mappers.getMapper( ErroneousFruitMapper2.class );

    @BeanMapping(resultType = Banana.class)
    @Mapping(target = "type", ignore = true)
    Apple map(AppleDto source);

}
