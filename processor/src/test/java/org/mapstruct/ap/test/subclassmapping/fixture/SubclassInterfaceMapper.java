/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping.fixture;

import org.mapstruct.Mapper;
import org.mapstruct.SubclassMapping;

@Mapper
public interface SubclassInterfaceMapper {

    @SubclassMapping( source = SubSource.class, target = SubTarget.class )
    @SubclassMapping( source = SubSourceOther.class, target = SubTargetOther.class )
    InterfaceParentTarget map(InterfaceParentSource item);
}
