/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4082.jdk21;

import java.util.List;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.Compiler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@WithClasses({ Source.class, Target.class, SourceMapper.class })
@IssueKey("4082")
class Issue4082Test {

    @ProcessorTest(Compiler.JDK)
    void shouldMapFirstAndLastFromSequencedCollection() {
        Source source = new Source( List.of( "alpha", "beta", "gamma" ) );

        Target target = SourceMapper.INSTANCE.map( source );

        assertThat( target.getFirst() ).isEqualTo( "alpha" );
        assertThat( target.getLast() ).isEqualTo( "gamma" );
    }

    @ProcessorTest(Compiler.JDK)
    void shouldReturnNullWhenItemsListIsNull() {
        Source source = new Source( null );

        Target target = SourceMapper.INSTANCE.map( source );

        assertThat( target.getFirst() ).isNull();
        assertThat( target.getLast() ).isNull();
    }

    @ProcessorTest(Compiler.JDK)
    void shouldThrowWhenItemsListIsEmpty() {
        Source source = new Source( List.of() );

        assertThatThrownBy( () -> SourceMapper.INSTANCE.map( source ) )
            .isInstanceOf( java.util.NoSuchElementException.class );
    }
}
