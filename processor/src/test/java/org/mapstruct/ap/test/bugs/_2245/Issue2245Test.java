/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2245;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2245")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    TestMapper.class
})
public class Issue2245Test {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( TestMapper.class );

    @Test
    public void shouldGenerateSourceGetMethodOnce() {

        TestMapper.Tenant tenant =
            TestMapper.INSTANCE.map( new TestMapper.TenantDTO( new TestMapper.Inner( "acme" ) ) );

        assertThat( tenant ).isNotNull();
        assertThat( tenant.getId() ).isEqualTo( "acme" );

    }
}
