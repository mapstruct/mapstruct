/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._375;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/375.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "375" )
public class Issue375Test {

    @ProcessorTest
    @WithClasses( { Issue375Mapper.class, Source.class, Target.class, Int.class, Case.class } )
    public void shouldForgeNewMappings() {
    }
}
