/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._843;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper( nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS )
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper( TagMapper.class );

    @Mapping(target = "date", source = "gitlabTag.commit.authoredDate")
    TagInfo gitlabTagToTagInfo(GitlabTag gitlabTag);
}
