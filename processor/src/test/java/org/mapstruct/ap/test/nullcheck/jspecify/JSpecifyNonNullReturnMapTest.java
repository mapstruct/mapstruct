/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1243")
@WithJSpecify
class JSpecifyNonNullReturnMapTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNonNullReturnMapMapper.class
    })
    void nonNullReturnMapForcesEmptyDefault() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNonNullReturnMapMapper.class );

        Map<String, NullMarkedTargetBean> fromNull = JSpecifyNonNullReturnMapMapper.INSTANCE.mapAll( null );

        assertThat( fromNull ).isEmpty();

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        Map<String, NullMarkedTargetBean> fromSource =
            JSpecifyNonNullReturnMapMapper.INSTANCE.mapAll( Collections.singletonMap( "k", source ) );

        assertThat( fromSource ).hasSize( 1 );
        assertThat( fromSource.get( "k" ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
