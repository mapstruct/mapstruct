/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2807.spring;

import org.mapstruct.ap.test.bugs._2807.spring.after.AfterMethod;
import org.mapstruct.ap.test.bugs._2807.spring.before.BeforeMethod;
import org.mapstruct.ap.test.bugs._2807.spring.beforewithtarget.BeforeWithTarget;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;

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
