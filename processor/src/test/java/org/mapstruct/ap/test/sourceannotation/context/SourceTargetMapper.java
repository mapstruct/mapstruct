/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.sourceannotation.context;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.SourceAnnotation;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SourceTargetMapper {
    SourceTargetMapper INSTANCE = Mappers.getMapper( SourceTargetMapper.class );
    String CONFIDENTIAL_MARKER = "<confidential>";

    Target sourceToTarget(Source source, @Context String accessLevel);

    default String mapProperty(String source, @Context String accessLevel,
                               @SourceAnnotation Confidential confidential) {
        if ( confidential == null ) {
            return source;
        }

        String requiredLevel = confidential.value();

        // business logic follows

        switch ( requiredLevel ) {
            case "company":
                return "company".equals( accessLevel ) || "management".equals( accessLevel )
                    ? source : CONFIDENTIAL_MARKER;
            case "management":
                return "management".equals( accessLevel ) ? source : CONFIDENTIAL_MARKER;
            default:
                return source;
        }
    }
}
