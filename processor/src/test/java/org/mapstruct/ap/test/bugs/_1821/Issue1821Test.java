/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1821;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

@IssueKey("1797")
@RunWith( AnnotationProcessorTestRunner.class)
@WithClasses( Issue1821Mapper.class )
public class Issue1821Test {

    @Test
    public void shouldNotGiveNullPtr() {
        Issue1821Mapper.INSTANCE.map( new Issue1821Mapper.Source() );
    }

}
