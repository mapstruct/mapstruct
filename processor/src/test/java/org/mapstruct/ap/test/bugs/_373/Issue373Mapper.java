/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._373;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface Issue373Mapper {

    @Mapping(target = "countryReference", source = "branchLocation.country")
    ResultDto toResultDto(Branch entity);

}
