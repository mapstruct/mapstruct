/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author Filip Hrisafov
 */
@Mapper(unmappedSourcePolicy = ReportingPolicy.ERROR)
public interface MemberMapper {

    MemberMapper INSTANCE = Mappers.getMapper( MemberMapper.class );

    MemberEntity fromRecord(MemberDto record);

    MemberDto toRecord(MemberEntity entity);

}
