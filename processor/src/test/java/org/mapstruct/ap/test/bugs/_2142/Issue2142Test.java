/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2142;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2142")
@WithClasses(Issue2142Mapper.class)
public class Issue2142Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( Issue2142Mapper.class );

    @ProcessorTest
    public void underscorePrefixShouldBeStrippedFromGeneratedLocalVariables() {
        Issue2142Mapper._Target target = Issue2142Mapper.INSTANCE.map( new Issue2142Mapper.Source( "value1" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "value1" );
    }
}
