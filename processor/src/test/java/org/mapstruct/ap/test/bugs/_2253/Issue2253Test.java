/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2253;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2253")
@WithClasses( {
    TestMapper.class,
} )
public class Issue2253Test {

    @ProcessorTest
    public void shouldNotTreatMatchedSourceParameterAsBean() {

        TestMapper.Person person = TestMapper.INSTANCE.map( new TestMapper.PersonDto( 20 ), "Tester" );

        assertThat( person.getAge() ).isEqualTo( 20 );
        assertThat( person.getName() ).isEqualTo( "Tester" );
    }
}
