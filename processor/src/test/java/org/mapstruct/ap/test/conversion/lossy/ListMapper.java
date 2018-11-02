/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.lossy;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( typeConversionPolicy = ReportingPolicy.WARN )
public interface ListMapper {

    List<BigInteger> map(List<BigDecimal> in);
}
