/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.attributereference;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface ErroneousMapper2 {

    @Mapping( target = "foo", ignore = true )
    @Mapping( target = "foo", constant = "5" )
    Target sourceToTarget( Source source );

}
