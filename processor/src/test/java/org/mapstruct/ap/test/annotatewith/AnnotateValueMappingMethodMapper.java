/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotatewith;

import org.mapstruct.AnnotateWith;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ValueMapping;
import org.mapstruct.ValueMappings;

/**
 * @author orange add
 */
@Mapper
public interface AnnotateValueMappingMethodMapper {

    @ValueMappings({
            @ValueMapping(target = "EXISTING", source = "EXISTING"),
            @ValueMapping( source = MappingConstants.ANY_REMAINING, target = "OTHER_EXISTING" )
    })
    @AnnotateWith(CustomMethodOnlyAnnotation.class)
    AnnotateWithEnum map(String str);
}
