/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2125;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper
public interface Issue2125ErroneousMapper {

    @Mapping(target = "issueId", qualifiedByName = "mapIssueNumber")
    Comment cloneWithQualifier(Comment comment, Issue issue);

    @Named("mapIssueNumber")
    default Integer mapIssueNumber(Integer issueNumber) {
        return issueNumber != null ? issueNumber + 1 : null;
    }

}
