/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3852;

import java.util.Optional;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Dennis Melzer
 */
@WithClasses({
    Issue3852Mapper.class,
    Source.class,
    Target.class
})
@WithSpring
@IssueKey("3852")
public class Issue3852Test {

    @ProcessorTest
    public void shouldOptionalNotNull() {
        Target target = Issue3852Mapper.INSTANCE.map( null );

        assertThat( target.getSomeString() ).isEmpty();
        assertThat( target.getSomeInteger() ).isEmpty();
        assertThat( target.getSomeDouble() ).isEmpty();
        assertThat( target.getSomeBoolean() ).isEmpty();
    }

    @ProcessorTest
    public void shouldMapOptional() {
        Source source = new Source(
            Optional.of( "someString" ),
            Optional.of( 10 ),
            Optional.of( 11D ),
            Optional.of( Boolean.TRUE )
        );
        Target target = Issue3852Mapper.INSTANCE.map( source );

        assertThat( target.getSomeString() ).contains( "someString" );
        assertThat( target.getSomeInteger() ).contains( 10 );
        assertThat( target.getSomeDouble() ).contains( 11D );
        assertThat( target.getSomeBoolean() ).contains( Boolean.TRUE );
    }
}
