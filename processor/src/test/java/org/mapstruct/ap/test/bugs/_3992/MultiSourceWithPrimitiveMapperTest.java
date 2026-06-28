/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3992;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for primitive parameters when generating early return
 *
 * @author Soumik Sarker
 */
public class MultiSourceWithPrimitiveMapperTest {

    @IssueKey("3992")
    @ProcessorTest
    @WithClasses({
        MultiSourceWithPrimitiveMapper.class
    })
    void shouldMapPrimitivesEvenIfAllReferenceParametersAreNull() {
        MultiSourceWithPrimitiveMapper.Target target = MultiSourceWithPrimitiveMapper.INSTANCE.map(
            null, null, null, 10, 2, true
        );

        assertThat( target ).isNotNull();
        assertThat( target.getSku() ).isNull();
        assertThat( target.getName() ).isNull();
        assertThat( target.getCategoryId() ).isNull();
        assertThat( target.getSize() ).isEqualTo( 10 );
        assertThat( target.getPageNumber() ).isEqualTo( 2 );
        assertThat( target.isDraft() ).isTrue();
    }
}
