/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2197;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2197")
@WithClasses(Issue2197Mapper.class)
public class Issue2197Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( Issue2197Mapper.class );

    @ProcessorTest
    public void underscoreAndDigitPrefixShouldBeStrippedFromGeneratedLocalVariables() {
        Issue2197Mapper._0Target target = Issue2197Mapper.INSTANCE.map( new Issue2197Mapper.Source( "value1" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "value1" );
    }
}
