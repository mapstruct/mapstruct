/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1243")
@WithJSpecify
class JSpecifyStreamMethodTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        NullMarkedSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyStreamMethodMapper.class
    })
    void streamMethodWithNonNullSourceSkipsMethodGuard() {
        generatedSource.addComparisonToFixtureFor( JSpecifyStreamMethodMapper.class );

        NullMarkedSourceBean source = new NullMarkedSourceBean();
        source.setNonNullByDefault( "value" );

        List<NullMarkedTargetBean> targets = JSpecifyStreamMethodMapper.INSTANCE
            .mapAll( Stream.of( source ) )
            .collect( Collectors.toList() );

        assertThat( targets ).hasSize( 1 );
        assertThat( targets.get( 0 ).getNonNullByDefault() ).isEqualTo( "value" );
    }
}
