/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.records.module2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.itest.records.module1.SourceRootRecord;
import org.mapstruct.itest.records.module1.SourceNestedRecord;

public class RecordsTest {

    @Test
    public void shouldMap() {
        SourceRootRecord source = new SourceRootRecord( new SourceNestedRecord( "test" ) );
        TargetRootRecord target = RecordInterfaceIssueMapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.nested() ).isNotNull();
        assertThat( target.nested().field() ).isEqualTo( "test" );
    }

}
