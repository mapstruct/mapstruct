/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.java8stream.wildcard;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Stream;

import org.mapstruct.Mapper;

/**
 *
 * @author Filip Hrisafov
 */
@Mapper
public interface ErroneousIterableExtendsBoundTargetMapper {

    List<? extends BigDecimal> map(Stream<BigDecimal> in);
}
