/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.sourcecheck;

import java.util.Arrays;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * A non-null result of the mapping method that is used for a property does not say anything about the nullability
 * of the source property. The source null / presence check has to be decided based on the source property.
 */
@WithJSpecify
@WithClasses( {
    Source.class,
    Target.class
} )
class NonNullResultSourceCheckTest {

    @ProcessorTest
    @WithClasses( NonNullResultUpdateMapper.class )
    void shouldNullCheckNullableSourceForUpdateMethodWithNonNullResult() {
        Target target = new Target();
        target.setChild( new Target.Child() );

        NonNullResultUpdateMapper.INSTANCE.update( new Source(), target );

        assertThat( target.getChild() ).isNull();

        Source source = new Source();
        source.setChild( new Source.Child() );
        source.getChild().setValue( "value" );
        target.setChild( new Target.Child() );

        NonNullResultUpdateMapper.INSTANCE.update( source, target );

        assertThat( target.getChild() ).isNotNull();
        assertThat( target.getChild().getValue() ).isEqualTo( "value" );
    }

    @ProcessorTest
    @WithClasses( NonNullResultMethodMapper.class )
    void shouldApplyDefaultValueForNullableSourceAndMethodWithNonNullResult() {
        Target target = NonNullResultMethodMapper.INSTANCE.map( new Source() );

        assertThat( target.getName() ).isEqualTo( "default" );

        Source source = new Source();
        source.setName( " name " );
        target = NonNullResultMethodMapper.INSTANCE.map( source );

        assertThat( target.getName() ).isEqualTo( "name" );
    }

    @ProcessorTest
    @WithClasses( NonNullResultMethodMapper.class )
    void shouldUsePresenceCheckForNullableSourceAndMethodWithNonNullResult() {
        Source source = new Source();
        source.setNick( "" );
        Target target = NonNullResultMethodMapper.INSTANCE.map( source );

        assertThat( target.getNick() ).isEqualTo( "initial" );

        source.setNick( " nick " );
        target = NonNullResultMethodMapper.INSTANCE.map( source );

        assertThat( target.getNick() ).isEqualTo( "nick" );
    }

    @ProcessorTest
    @WithClasses( NonNullResultMethodMapper.class )
    void shouldNullCheckNullableSourceCollectionForMethodWithNonNullResult() {
        Target target = NonNullResultMethodMapper.INSTANCE.map( new Source() );

        assertThat( target.getNumbers() ).isNull();

        Source source = new Source();
        source.setNumbers( Arrays.asList( 1, 2 ) );
        target = NonNullResultMethodMapper.INSTANCE.map( source );

        assertThat( target.getNumbers() ).containsExactly( "1", "2" );
    }
}
