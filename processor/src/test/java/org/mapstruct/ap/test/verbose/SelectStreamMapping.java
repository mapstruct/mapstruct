/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.verbose;

import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SelectStreamMapping {

    SelectStreamMapping INSTANCE = Mappers.getMapper( SelectStreamMapping.class );

    Stream<TargetElement> map(Stream<SourceElement> source);

    default TargetElement map(SourceElement sourceKey) {
        return null;
    }

    class SourceElement {
    }

    class TargetElement {
    }

}

