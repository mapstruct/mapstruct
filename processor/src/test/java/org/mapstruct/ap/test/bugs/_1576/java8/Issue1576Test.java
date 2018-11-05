/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1576.java8;

import java.util.Date;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@IssueKey("1576")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses( { Issue1576Mapper.class, Source.class, Target.class })
public class Issue1576Test {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        Issue1576Mapper.class
    );

    @Test
    public void testLocalDateTimeIsImported() {

        Issue1576Mapper.INSTANCE.map( new Source() );
    }

}
