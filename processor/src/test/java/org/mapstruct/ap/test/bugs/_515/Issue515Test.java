/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._515;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/515.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "515" )
public class Issue515Test {

    @ProcessorTest
    @WithClasses( { Issue515Mapper.class, Source.class, Target.class } )
    public void shouldIgnoreParanthesesOpenInStringDefinition() {
    }
}
