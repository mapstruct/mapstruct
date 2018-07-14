/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1005;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue1005ErroneousAbstractResultTypeMapper {

    @BeanMapping(resultType = AbstractEntity.class)
    HasKey map(OrderDto orderDto);
}
