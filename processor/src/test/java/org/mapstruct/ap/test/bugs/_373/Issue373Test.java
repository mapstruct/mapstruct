/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._373;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/373.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "373" )
public class Issue373Test {

    @ProcessorTest
    @WithClasses( { Issue373Mapper.class, Branch.class, BranchLocation.class, Country.class, ResultDto.class } )
    public void shouldForgeCorrectEntityBranchLocationCountry() {
    }
}
