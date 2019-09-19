/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.expressions.java;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper( uses = QualifierProvider.class, unmappedTargetPolicy = ReportingPolicy.IGNORE )
public interface ErroneousSourceTargetMapperExpressionAndQualifiers {

    @Mappings( {
        @Mapping( target = "anotherProp", expression = "java( s.getClass().getName() )", qualifiedByName = "toUpper" ),
        @Mapping( target = "timeAndFormat", ignore = true )
    } )
    Target sourceToTargetWithExpressionAndNamedQualifier(Source s, @MappingTarget Target t);

    @Mappings( {
        @Mapping( target = "anotherProp", expression = "java( s.getClass().getName() )",
            qualifiedBy = QualifierProvider.ToUpper.class ),
        @Mapping( target = "timeAndFormat", ignore = true )
    } )
    Target sourceToTargetWithExpressionAndQualifier(Source s, @MappingTarget Target t);
}
