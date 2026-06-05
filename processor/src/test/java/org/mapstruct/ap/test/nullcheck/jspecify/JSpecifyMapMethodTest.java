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
class JSpecifyMapMethodTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyMapMethodMapper.class
    })
    void mapMethodWithNonNullSourceSkipsMethodGuard() {
        generatedSource.addComparisonToFixtureFor( JSpecifyMapMethodMapper.class );

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        Map<String, NullMarkedTargetBean> targets =
            JSpecifyMapMethodMapper.INSTANCE.mapAll( Collections.singletonMap( "k", source ) );

        assertThat( targets ).hasSize( 1 );
        assertThat( targets.get( "k" ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
