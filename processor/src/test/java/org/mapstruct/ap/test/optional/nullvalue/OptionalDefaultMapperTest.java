/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvalue;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dennis Melzer
 */
@WithClasses({
    OptionalDefaultMapper.class,
    Source.class,
    Target.class
})
@IssueKey("3852")
public class OptionalDefaultMapperTest {

    @ProcessorTest
    public void shouldOptionalNotNull() {
        Source source = new Source( null, null, null, null, null, null, null );
        Target target = OptionalDefaultMapper.INSTANCE.map( source );

        assertThat( target.getSomeString() ).isEmpty();
        assertThat( target.getSomeInteger() ).isEmpty();
        assertThat( target.getSomeDouble() ).isEmpty();
        assertThat( target.getSomeBoolean() ).isEmpty();
        assertThat( target.getSomeIntValue() ).isEmpty();
        assertThat( target.getSomeDoubleValue() ).isEmpty();
        assertThat( target.getSomeLongValue() ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapOptional() {
        Source source = new Source( "someString", 10, 11D, Boolean.TRUE, 10, 100D, 200L );
        Target target = OptionalDefaultMapper.INSTANCE.map( source );

        assertThat( target.getSomeString() ).contains( "someString" );
        assertThat( target.getSomeInteger() ).contains( 10 );
        assertThat( target.getSomeDouble() ).contains( 11D );
        assertThat( target.getSomeBoolean() ).contains( Boolean.TRUE );
        assertThat( target.getSomeIntValue() ).hasValue( 10 );
        assertThat( target.getSomeDoubleValue() ).hasValue( 100 );
        assertThat( target.getSomeLongValue() ).hasValue( 200 );
    }

}
