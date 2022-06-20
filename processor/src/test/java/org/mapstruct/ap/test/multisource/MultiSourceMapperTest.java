/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.multisource;

import java.util.Collections;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
public class MultiSourceMapperTest {

    @IssueKey("2005")
    @ProcessorTest
    @WithClasses({
        MultiSourceMapper.class
    })
    void shouldBeAbleToMapFromPrimitiveAndCollectionAsMultiSource() {
        MultiSourceMapper.Target target = MultiSourceMapper.INSTANCE.mapFromPrimitiveAndCollection(
            10,
            Collections.singleton( "test" )
        );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 10 );
        assertThat( target.getElements() ).containsExactly( "test" );

        target = MultiSourceMapper.INSTANCE.mapFromCollectionAndPrimitive(
            Collections.singleton( "otherTest" ),
            20
        );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 20 );
        assertThat( target.getElements() ).containsExactly( "otherTest" );
    }
}
