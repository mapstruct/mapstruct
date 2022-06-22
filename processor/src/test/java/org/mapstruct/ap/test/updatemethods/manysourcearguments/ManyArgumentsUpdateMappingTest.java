/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.manysourcearguments;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2274")
@WithClasses({
    ExampleMapper.class,
    ExampleMember.class,
    ExampleSource.class,
    ExampleTarget.class,
})
public class ManyArgumentsUpdateMappingTest {

    @ProcessorTest
    public void shouldUpdateToNullIfSourceParametersAreNull() {
        ExampleTarget target = new ExampleTarget();
        target.setBirthday( LocalDate.of( 2020, Month.NOVEMBER, 15 ) );
        target.setMember( new ExampleMember( "test" ) );
        ExampleMapper.INSTANCE.update(
            target,
            new ExampleSource( LocalDate.of( 2020, Month.AUGUST, 30 ) ),
            null
        );

        assertThat( target.getBirthday() ).isEqualTo( LocalDate.of( 2020, Month.AUGUST, 30 ) );
        assertThat( target.getMember() ).isNull();

        ExampleMapper.INSTANCE.update(
            target,
            new ExampleSource( null ),
            new ExampleMember( "second test" )
        );

        assertThat( target.getBirthday() ).isNull();
        assertThat( target.getMember() ).isNotNull();
        assertThat( target.getMember().getName() ).isEqualTo( "second test" );
    }
}
