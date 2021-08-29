/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.subclassmapping;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubClassMapping;

@Mapper
public interface SubclassMapper {

    @SubClassMapping( source = SourceSubclass.class, target = Target.class )
    @Mapping( target = "target1", source = "property1" )
    @Mapping( target = "target2", source = "property2" )
    @Mapping( target = "target3", source = "property3" )
    @Mapping( target = "target4", ignore = true )
    @Mapping( target = "target5", ignore = true )
    Target mapSuperclass(Source source);

    @InheritConfiguration( name = "mapSuperclass" )
    @Mapping( target = "target4", source = "subclassProperty" )
    @Mapping( target = "target5" ) // Have to declare in order to override ignore with default behavior
    Target mapSubclass(SourceSubclass source);
}
