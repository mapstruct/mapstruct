/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.mapperconfig;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

/**
 *
 * @author Sjaak Derksen
 */
@MapperConfig(uses = { CustomMapperViaMapperConfig.class }, unmappedTargetPolicy = ReportingPolicy.ERROR )
public class CentralConfig {

}
