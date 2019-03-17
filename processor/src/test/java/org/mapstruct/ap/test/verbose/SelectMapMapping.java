/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.verbose;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SelectMapMapping {

    SelectMapMapping INSTANCE = Mappers.getMapper( SelectMapMapping.class );

    Map<TargetKey, TargetValue> map(Map<SourceKey, SourceValue> source);

    default TargetKey map(SourceKey sourceKey) {
        return null;
    }

    default TargetValue map(SourceValue sourceValue) {
        return null;
    }

    class SourceKey {
    }

    class SourceValue {
    }

    class TargetKey {
    }

    class TargetValue {
    }
}

