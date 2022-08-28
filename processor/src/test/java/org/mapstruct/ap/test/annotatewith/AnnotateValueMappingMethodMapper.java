/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;

/**
 * @author orange add
 * @date 2022/8/27 22:57
 */
@Mapper
public interface AnnotateValueMappingMethodMapper {

    @ValueMappings({
            @ValueMapping(target = "SPECIAL", source = "EXTRA"),
            @ValueMapping(target = "DEFAULT", source = "STANDARD"),
            @ValueMapping(target = "DEFAULT", source = "NORMAL")
    })
    @AnnotateWith(CustomMethodOnlyAnnotation.class)
    ExternalOrderType orderTypeToExternalOrderType(OrderType orderType);
}
