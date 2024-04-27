/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DeepNestedMapper {
    public static DeepNestedMapper INSTANCE = Mappers.getMapper( DeepNestedMapper.class );

    @Mapping( target = "target.nestedSecondTargetChild.autoMapChild", source = "source.sourceInnerChild.nestedSecondSourceChild" )
    @Mapping( target = "target.nestedTargetChild", source = "source.sourceInnerChild.nestedSourceChild" )
    @Mapping( target = "targetCollection", source = "collectionContainer.source" )
    TargetContainer map(SourceContainer source);

}
