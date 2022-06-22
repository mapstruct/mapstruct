/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

import static org.mapstruct.SubclassExhaustiveStrategy.RUNTIME_EXCEPTION;

import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper
public interface SubclassAbstractMapper {

    @BeanMapping( subclassExhaustiveStrategy = RUNTIME_EXCEPTION )
    @SubclassMapping( source = SubSource.class, target = SubTarget.class )
    @SubclassMapping( source = SubSourceOther.class, target = SubTargetOther.class )
    AbstractParentTarget map(AbstractParentSource item);

    @SubclassMapping( source = SubTargetSeparate.class, target = SubSourceSeparate.class )
    @InheritInverseConfiguration
    @SubclassMapping( source = SubTargetOther.class, target = SubSourceOverride.class )
    AbstractParentSource map(AbstractParentTarget item);
}
