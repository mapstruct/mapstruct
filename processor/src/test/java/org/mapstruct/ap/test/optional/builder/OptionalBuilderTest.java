/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.builder;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
class OptionalBuilderTest {

    @ProcessorTest
    @WithClasses(SimpleOptionalBuilderMapper.class)
    void simpleOptionalBuilder() {
        Optional<SimpleOptionalBuilderMapper.Target> targetOpt = SimpleOptionalBuilderMapper.INSTANCE.map( null );
        assertThat( targetOpt ).isEmpty();

        targetOpt = SimpleOptionalBuilderMapper.INSTANCE.map( new SimpleOptionalBuilderMapper.Source( "test" ) );
        assertThat( targetOpt ).isNotEmpty();
        SimpleOptionalBuilderMapper.Target target = targetOpt.get();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }
}
