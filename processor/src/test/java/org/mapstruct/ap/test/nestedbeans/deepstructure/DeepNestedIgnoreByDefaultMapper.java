/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.deepstructure;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface DeepNestedIgnoreByDefaultMapper {

    @Mapping( target = "target.nestedSecondTargetChild.autoMapChild", source = "source.sourceInnerChild.nestedSecondSourceChild" )
    @Mapping( target = "target.nestedTargetChild", source = "source.sourceInnerChild.nestedSourceChild" )
    @Mapping( target = "targetCollection", source = "collectionContainer.source" )
    @BeanMapping( ignoreByDefault = true )
    TargetContainer map(SourceContainer source);

}
