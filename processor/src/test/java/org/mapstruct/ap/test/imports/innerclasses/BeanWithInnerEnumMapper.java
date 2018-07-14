/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BeanWithInnerEnumMapper {

    BeanWithInnerEnumMapper INSTANCE = Mappers.getMapper( BeanWithInnerEnumMapper.class );

    BeanWithInnerEnum fromFacade(BeanFacade beanFacade);

    BeanFacade toFacade(BeanWithInnerEnum beanWithInnerEnum);
}
