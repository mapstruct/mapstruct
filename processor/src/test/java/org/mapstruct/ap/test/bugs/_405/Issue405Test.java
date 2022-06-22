/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._405;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Reproducer for https://github.com/mapstruct/mapstruct/issues/405.
 *
 * @author Sjaak Derksen
 */
@IssueKey( "405" )
public class Issue405Test {

    @ProcessorTest
    @WithClasses( { EntityFactory.class, Person.class, People.class, PersonMapper.class } )
    public void shouldGenerateFactoryCorrectMethodForIterables() {
    }
}
