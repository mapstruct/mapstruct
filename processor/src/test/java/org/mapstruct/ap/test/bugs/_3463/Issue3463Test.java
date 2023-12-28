/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3463;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3463")
@WithClasses({
    EntityBuilder.class,
    Issue3463Mapper.class,
    Person.class,
    PersonDto.class
})
class Issue3463Test {

    @ProcessorTest
    void shouldUseInterfaceBuildMethod() {
        Person person = Issue3463Mapper.INSTANCE.map( new PersonDto( "Tester" ) );

        assertThat( person ).isNotNull();
        assertThat( person.getName() ).isEqualTo( "Tester" );
    }
}
