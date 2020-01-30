/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mappingcontrol;

import org.mapstruct.MapperConfig;
import org.mapstruct.control.NoComplexMapping;

/**
 * @author Sjaak Derksen
 */
@MapperConfig( mappingControl = NoComplexMapping.class )
public interface Config {
}
