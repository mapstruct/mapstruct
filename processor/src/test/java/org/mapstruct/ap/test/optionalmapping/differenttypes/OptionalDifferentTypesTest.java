/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.differenttypes;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalDifferentTypesMapper.class,
    Source.class,
    Target.class })
public class OptionalDifferentTypesTest {

    @ProcessorTest
    public void constructorOptionalToOptional_present() {
        Source source = new Source( Optional.of( new Source.SubType( "some value" ) ), null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void constructorOptionalToOptional_empty() {
        Source source = new Source( Optional.empty(), null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    // TODO Contentious
    // Should null Optional map to null Optional, or explicitly to empty?
    @ProcessorTest
    public void constructorOptionalToOptional_null() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void constructorOptionalToNonOptional_present() {
        Source source = new Source( null, Optional.of( new Source.SubType( "some value" ) ), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void constructorOptionalToNonOptional_empty() {
        Source source = new Source( null, Optional.empty(), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorOptionalToNonOptional_null() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorNonOptionalToOptional_nonNull() {
        Source source = new Source( null, null, new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void constructorNonOptionalToOptional_null() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToOptional_present() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void optionalToOptional_empty() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    // TODO Contentious
    // Should null Optional map to null Optional, or explicitly to empty?
    @ProcessorTest
    public void optionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToNonOptional_present() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void optionalToNonOptional_empty() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void optionalToNonOptional_null() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nonOptionalToOptional_nonNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void nonOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToOptional_present() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void publicOptionalToOptional_empty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    // TODO Contentious
    // Should null Optional map to null Optional, or explicitly to empty?
    @ProcessorTest
    public void publicOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToNonOptional_present() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void publicOptionalToNonOptional_empty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    public void publicOptionalToNonOptional_null() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    public void publicNonOptionalToOptional_nonNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = new Source.SubType( "some value" ) ;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void publicNonOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = null ;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }
    
}
