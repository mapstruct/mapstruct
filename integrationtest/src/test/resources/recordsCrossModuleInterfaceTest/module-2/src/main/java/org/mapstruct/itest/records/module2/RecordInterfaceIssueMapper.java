/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records.module2;

import org.mapstruct.itest.records.module1.SourceRootRecord;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecordInterfaceIssueMapper {

    RecordInterfaceIssueMapper INSTANCE = Mappers.getMapper(RecordInterfaceIssueMapper.class);

    TargetRootRecord map(SourceRootRecord source);
}
