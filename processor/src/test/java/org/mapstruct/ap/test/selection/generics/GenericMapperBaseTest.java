/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.generics;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@WithClasses( {
    GenericMapperBase.class
} )
@IssueKey( "2755" )
class GenericMapperBaseTest {

    @ProcessorTest
    void generatesCompilableCode() {
    }

}
