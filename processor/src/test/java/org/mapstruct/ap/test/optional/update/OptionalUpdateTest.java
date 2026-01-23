/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.update;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Source.class,
    Target.class,
})
class OptionalUpdateTest {

    @ProcessorTest
    @WithClasses({ OptionalUpdateMapper.class })
    void defaultUpdate() {
        Target target = new Target();
        OptionalUpdateMapper.INSTANCE.map( null, target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.getOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "initial" );
        assertThat( target.publicOptionalToOptional ).hasValue( "initial" );

        Source source = new Source();
        source.setNonOptionalToOptional( "some value" );
        source.setOptionalToOptional( Optional.of( "some value" ) );
        source.publicNonOptionalToOptional = "some value";
        source.publicOptionalToOptional = Optional.of( "some value" );

        OptionalUpdateMapper.INSTANCE.map( source, target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "some value" );
        assertThat( target.getOptionalToOptional() ).hasValue( "some value" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "some value" );
        assertThat( target.publicOptionalToOptional ).hasValue( "some value" );

        target = new Target();
        OptionalUpdateMapper.INSTANCE.map( new Source(), target );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
        assertThat( target.getOptionalToOptional() ).isEmpty();
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    @WithClasses({ OptionalUpdateNullValuePropertyToDefaultMapper.class })
    void nullValuePropertyToDefault() {
        Target target = new Target();
        OptionalUpdateNullValuePropertyToDefaultMapper.INSTANCE.map( null, target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.getOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "initial" );
        assertThat( target.publicOptionalToOptional ).hasValue( "initial" );

        Source source = new Source();
        source.setNonOptionalToOptional( "some value" );
        source.setOptionalToOptional( Optional.of( "some value" ) );
        source.publicNonOptionalToOptional = "some value";
        source.publicOptionalToOptional = Optional.of( "some value" );

        OptionalUpdateNullValuePropertyToDefaultMapper.INSTANCE.map( source, target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "some value" );
        assertThat( target.getOptionalToOptional() ).hasValue( "some value" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "some value" );
        assertThat( target.publicOptionalToOptional ).hasValue( "some value" );

        target = new Target();
        OptionalUpdateNullValuePropertyToDefaultMapper.INSTANCE.map( new Source(), target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "" );
        assertThat( target.getOptionalToOptional() ).hasValue( "" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "" );
        assertThat( target.publicOptionalToOptional ).hasValue( "" );


    }

    @ProcessorTest
    @WithClasses({ OptionalUpdateNullValuePropertyIgnoreMapper.class })
    void nullValuePropertyIgnore() {
        Target target = new Target();
        OptionalUpdateNullValuePropertyIgnoreMapper.INSTANCE.map( null, target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.getOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "initial" );
        assertThat( target.publicOptionalToOptional ).hasValue( "initial" );

        Source source = new Source();
        source.setNonOptionalToOptional( "some value" );
        source.setOptionalToOptional( Optional.of( "some value" ) );
        source.publicNonOptionalToOptional = "some value";
        source.publicOptionalToOptional = Optional.of( "some value" );

        OptionalUpdateNullValuePropertyIgnoreMapper.INSTANCE.map( source, target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "some value" );
        assertThat( target.getOptionalToOptional() ).hasValue( "some value" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "some value" );
        assertThat( target.publicOptionalToOptional ).hasValue( "some value" );

        target = new Target();
        OptionalUpdateNullValuePropertyIgnoreMapper.INSTANCE.map( new Source(), target );
        assertThat( target.getNonOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.getOptionalToOptional() ).hasValue( "initial" );
        assertThat( target.publicNonOptionalToOptional ).hasValue( "initial" );
        assertThat( target.publicOptionalToOptional ).hasValue( "initial" );


    }
}
