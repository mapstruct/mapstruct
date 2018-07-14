/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.nullvaluecheckstrategy;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;

/**
 *
 * @author Sjaak Derksen
 */
@MapperConfig( nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface RockFestivalMapperConfig {

}
