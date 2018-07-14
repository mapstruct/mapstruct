/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueMappingStrategy;

/**
 * @author Sjaak Derksen
 */
@MapperConfig(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public class CentralConfig {

}
