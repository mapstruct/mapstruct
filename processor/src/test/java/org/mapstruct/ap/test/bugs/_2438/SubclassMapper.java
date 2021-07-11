/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2438;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.SubClassMapping;

@Mapper
public interface SubclassMapper {

    @SubClassMapping( sourceClass = SourceSubclass.class, targetClass = Target.class )
    @Mapping( target = "target1", source = "property1" )
    @Mapping( target = "target2", source = "property2" )
    @Mapping( target = "target3", source = "property3" )
    @Mapping( target = "target4", expression = "java(null)" )
    @Mapping( target = "target5", expression = "java(null)" )
    Target mapSuperclass(Source source);

    @InheritConfiguration( name = "mapSuperclass" )
    @Mapping( target = "target4", source = "subclassProperty" )
    @Mapping( target = "target5" ) // Have to declare in order to override with default behavior
    Target mapSubclass(SourceSubclass source);
}
