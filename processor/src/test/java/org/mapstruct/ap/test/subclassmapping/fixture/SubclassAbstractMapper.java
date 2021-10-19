/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

import static org.mapstruct.SubclassExhaustiveStrategy.RUNTIME_EXCEPTION;

@Mapper
public interface SubclassAbstractMapper {

    @BeanMapping( subclassExhaustiveStrategy = RUNTIME_EXCEPTION )
    @SubclassMapping( source = SubSource.class, target = SubTarget.class )
    @SubclassMapping( source = SubSourceOther.class, target = SubTargetOther.class )
    AbstractParentTarget map(AbstractParentSource item);
}
