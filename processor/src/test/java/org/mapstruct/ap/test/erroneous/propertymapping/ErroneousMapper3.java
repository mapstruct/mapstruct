/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.propertymapping;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface ErroneousMapper3 {

    @BeanMapping( ignoreByDefault = true )
    @Mapping( target = "constant", constant = "constant" )
    Target map(Source source);
}
