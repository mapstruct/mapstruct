/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2124;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper
public interface Issue2124Mapper {

    Issue2124Mapper INSTANCE = Mappers.getMapper( Issue2124Mapper.class );

    @Mapping(target = "issueId", source = "comment.issueId", qualifiedByName = "mapped")
    CommitComment clone(CommitComment comment, String otherSource);

    @Named("mapped")
    default Integer mapIssueNumber(int issueNumber) {
        return issueNumber * 2;
    }
}
