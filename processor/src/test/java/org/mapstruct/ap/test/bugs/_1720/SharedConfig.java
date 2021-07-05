/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1720;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@MapperConfig
public interface SharedConfig {

    @Mappings(
            @Mapping(target = "fullName", source = "source.name")
    )
    Target map(Source source);

}
