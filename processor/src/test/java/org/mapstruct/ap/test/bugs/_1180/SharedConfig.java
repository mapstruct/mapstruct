/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1180;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 *
 * @author Sjaak Derksen
 */
@MapperConfig
public interface SharedConfig {

    @Mapping(target = "targetProperty", source = "sourceProperty.nonExistant")
    Target map(Source source);

}
