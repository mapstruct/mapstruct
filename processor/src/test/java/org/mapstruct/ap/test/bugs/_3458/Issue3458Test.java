/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3458;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.factory.Mappers;

@IssueKey( "3458" )
public class Issue3458Test {

    @ProcessorTest
    @WithClasses( { Person.class, PersonMapper.class, GenericMapper.class, IdentityMapper.class } )
    void overridenMethodDoesNotGetOverriddenAgain() {
        Person lead = new Person();

        Person result = Mappers.getMapper( PersonMapper.class ).map( lead );

        assertThat( result ).isSameAs( lead );
    }
}
