/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.updatemethods.manysourcearguments;

import java.time.LocalDate;
import java.time.Month;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2274")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    ExampleMapper.class,
    ExampleMember.class,
    ExampleSource.class,
    ExampleTarget.class,
})
public class ManyArgumentsUpdateMappingTest {

    @Test
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
