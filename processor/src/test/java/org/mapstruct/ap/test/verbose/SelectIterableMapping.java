/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.verbose;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SelectIterableMapping {

    SelectIterableMapping INSTANCE = Mappers.getMapper( SelectIterableMapping.class );

    List<TargetElement> map(List<SourceElement> source);

    default TargetElement map(SourceElement sourceKey) {
        return null;
    }

    class SourceElement {
    }

    class TargetElement {
    }

}

