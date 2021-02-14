/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1576.java8;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@IssueKey("1576")
@WithClasses( { Issue1576Mapper.class, Source.class, Target.class })
public class Issue1576Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        Issue1576Mapper.class
    );

    @ProcessorTest
    public void testLocalDateTimeIsImported() {

        Issue1576Mapper.INSTANCE.map( new Source() );
    }

}
