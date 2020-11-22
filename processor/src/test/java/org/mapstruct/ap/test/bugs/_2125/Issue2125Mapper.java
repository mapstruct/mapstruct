/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2125;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface Issue2125Mapper {

    Issue2125Mapper INSTANCE = Mappers.getMapper( Issue2125Mapper.class );

    // In this case the issueId from the Comment is used
    Comment clone(Comment comment, Integer issueId);

    // When source is not defined then we will use the issueId from the Comment,
    // same as when there was no mapping
    @Mapping(target = "issueId", qualifiedByName = "mapIssueNumber")
    Comment cloneWithQualifier(Comment comment, Integer issueId);

    // When source is defined then we will source the parameter name
    @Mapping(target = "issueId", source = "issueId", qualifiedByName = "mapIssueNumber")
    Comment cloneWithQualifierExplicitSource(Comment comment, Integer issueId);

    @Named("mapIssueNumber")
    default Integer mapIssueNumber(Integer issueNumber) {
        return issueNumber != null ? issueNumber + 1 : null;
    }

}
