/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1243")
@WithJSpecify
class JSpecifyNonNullReturnBeanTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses({
        JSpecifyNonNullReturnBeanSourceBean.class,
        NullMarkedTargetBean.class,
        JSpecifyNonNullReturnBeanMapper.class
    })
    void nonNullReturnForcesMapNullToDefault() {
        generatedSource.addComparisonToFixtureFor( JSpecifyNonNullReturnBeanMapper.class );

        NullMarkedTargetBean fromNull = JSpecifyNonNullReturnBeanMapper.INSTANCE.map( null );

        assertThat( fromNull ).isNotNull();
        assertThat( fromNull.getNonNullByDefault() ).isNull();

        JSpecifyNonNullReturnBeanSourceBean source = new JSpecifyNonNullReturnBeanSourceBean();
        source.setValue( "value" );

        NullMarkedTargetBean fromSource = JSpecifyNonNullReturnBeanMapper.INSTANCE.map( source );

        assertThat( fromSource ).isNotNull();
        assertThat( fromSource.getNonNullByDefault() ).isEqualTo( "value" );
    }
}
