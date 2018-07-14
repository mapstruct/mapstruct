/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._373;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/373.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "373" )
@RunWith(AnnotationProcessorTestRunner.class)
public class Issue373Test {

    @Test
    @WithClasses( { Issue373Mapper.class, Branch.class, BranchLocation.class, Country.class, ResultDto.class } )
    public void shouldForgeCorrectEntityBranchLocationCountry() {
    }
}
