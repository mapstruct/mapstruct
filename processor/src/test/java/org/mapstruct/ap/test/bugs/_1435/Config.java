/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1435;

import java.util.Objects;

import org.mapstruct.MapperConfig;
import org.mapstruct.Mapping;
import org.mapstruct.MappingInheritanceStrategy;

@MapperConfig(mappingInheritanceStrategy = MappingInheritanceStrategy.AUTO_INHERIT_FROM_CONFIG, imports = Objects.class)
public interface Config {
    @Mapping(expression = "java( Objects.equals( source.getName(), \"Rainbow Dash\" ) )", target = "rainbowDash")
    OutObject map(InObject source);

}
