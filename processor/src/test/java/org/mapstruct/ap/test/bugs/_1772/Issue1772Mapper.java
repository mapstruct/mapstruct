/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1772;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Sjaak Derksen
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface Issue1772Mapper {

    Issue1772Mapper INSTANCE = Mappers.getMapper( Issue1772Mapper.class );

    @Mapping(target = "nestedTarget.doubleNestedTarget", source = "nestedSource.doublyNestedSourceField" )
    Target map(Source source);
}
