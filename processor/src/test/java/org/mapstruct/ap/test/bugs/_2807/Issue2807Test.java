/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2807;

import org.mapstruct.ap.test.bugs._2807.after.AfterMethod;
import org.mapstruct.ap.test.bugs._2807.before.BeforeMethod;
import org.mapstruct.ap.test.bugs._2807.beforewithtarget.BeforeWithTarget;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.WithSpring;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2807" )
public class Issue2807Test {

    @ProcessorTest
    @WithSpring
    @WithClasses( { SpringLifeCycleMapper.class, BeforeMethod.class, BeforeWithTarget.class, AfterMethod.class } )
    void shouldCompile() {
    }
}
