/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.erroneous.supermappingwithsubclassmapper;

import org.mapstruct.Mapper;

@Mapper
public interface ErroneousMapper2 extends AbstractMapper<Source, Target> {
    @Override
    Target map(Source source);
}
