/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.collection.wildcard;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Mapper;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface ErroneousIterableSuperBoundSourceMapper {

    List<BigDecimal> map(List<? super BigDecimal> in);
}
