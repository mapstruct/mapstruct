/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2245;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2245")
@WithClasses({
    TestMapper.class
})
public class Issue2245Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( TestMapper.class );

    @ProcessorTest
    public void shouldGenerateSourceGetMethodOnce() {

        TestMapper.Tenant tenant =
            TestMapper.INSTANCE.map( new TestMapper.TenantDTO( new TestMapper.Inner( "acme" ) ) );

        assertThat( tenant ).isNotNull();
        assertThat( tenant.getId() ).isEqualTo( "acme" );

    }
}
