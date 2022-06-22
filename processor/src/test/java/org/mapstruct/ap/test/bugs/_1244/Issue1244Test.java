/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1244;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.factory.Mappers;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1244")
@WithClasses( SizeMapper.class )
public class Issue1244Test {

    @ProcessorTest
    public void properlyCreatesMapperWithSizeAsParameterName() {
        SizeMapper.SizeHolder sizeHolder = new SizeMapper.SizeHolder();
        sizeHolder.setSize( "size" );

        SizeMapper.SizeHolderDto dto = Mappers.getMapper( SizeMapper.class ).convert( sizeHolder );
        assertThat( dto.getSize() ).isEqualTo( "size" );
    }
}
