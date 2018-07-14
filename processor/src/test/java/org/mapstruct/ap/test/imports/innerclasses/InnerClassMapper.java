/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.innerclasses;

import org.mapstruct.Mapper;
import org.mapstruct.ap.test.imports.innerclasses.SourceWithInnerClass.SourceInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.TargetWithInnerClass.TargetInnerClass;
import org.mapstruct.ap.test.imports.innerclasses.TargetWithInnerClass.TargetInnerClass.TargetInnerInnerClass;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InnerClassMapper {

    InnerClassMapper INSTANCE = Mappers.getMapper( InnerClassMapper.class );

    TargetWithInnerClass sourceToTarget(SourceWithInnerClass source);

    TargetInnerClass innerSourceToInnerTarget(SourceInnerClass source);

    TargetInnerInnerClass innerSourceToInnerInnerTarget(SourceInnerClass source);

}
