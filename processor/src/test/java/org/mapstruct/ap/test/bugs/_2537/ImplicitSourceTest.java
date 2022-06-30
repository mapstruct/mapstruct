/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2537;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * implicit source-target mapping should list the source property as being mapped.
 *
 * @author Ben Zegveld
 */
@WithClasses( { UnmappedSourcePolicyWithImplicitSourceMapper.class } )
@IssueKey( "2537" )
public class ImplicitSourceTest {

    @ProcessorTest
    public void situationCompilesWithoutErrors() {
    }
}
