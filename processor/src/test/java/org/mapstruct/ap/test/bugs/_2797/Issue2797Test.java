/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2797;

import org.mapstruct.ap.test.bugs._2797.model.BasePerson;
import org.mapstruct.ap.test.bugs._2797.model.Example;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Ben Zegveld
 */
@IssueKey( "2797" )
@WithClasses( { ExampleDto.class, ExampleMapper.class, Example.class, BasePerson.class } )
public class Issue2797Test {

    @ProcessorTest
    void shouldCompile() {
    }
}
