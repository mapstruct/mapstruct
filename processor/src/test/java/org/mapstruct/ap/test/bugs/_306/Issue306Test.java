/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._306;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/306.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "306" )
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue306Test {

    @Test
    @WithClasses( { Issue306Mapper.class, Source.class, Target.class } )
    public void shouldForgeNewIterableMappingMethod() {
    }
}
