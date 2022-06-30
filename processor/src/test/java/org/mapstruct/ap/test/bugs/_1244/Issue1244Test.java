/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1244;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

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
