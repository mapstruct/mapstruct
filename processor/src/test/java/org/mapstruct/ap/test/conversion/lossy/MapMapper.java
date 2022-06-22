/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( typeConversionPolicy = ReportingPolicy.WARN )
public interface MapMapper {

    Map<Integer, Float> map(Map<Long, Double> in);
}
