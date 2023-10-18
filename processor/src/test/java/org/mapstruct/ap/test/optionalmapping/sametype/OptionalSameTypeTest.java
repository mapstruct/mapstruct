/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.sametype;

import java.util.Optional;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalSameTypeMapper.class,
    Source.class,
    Target.class })
public class OptionalSameTypeTest {

    @ProcessorTest
    public void constructorOptionalToOptional_present() {
        Source source = new Source( Optional.of( "some value" ), null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void constructorOptionalToOptional_empty() {
        Source source = new Source( Optional.empty(), null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    // TODO Contentious
    // Should null Optional map to null Optional, or explicitly to empty?
    // This one is a bit stranger than the "different types" case.
    // Because here, both the source and target are the same type:
    // Optional<String> -> Optional<String>, therefore no nested mapping is required.
    // By default, the mapper just generates `target.prop = source.prop`.
    @ProcessorTest
    public void constructorOptionalToOptional_null() {
        Source source = new Source( null, null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorOptionalToNonOptional_present() {
        Source source = new Source( null, Optional.of( "some value" ), null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void constructorOptionalToNonOptional_empty() {
        Source source = new Source( null, Optional.empty(), null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorOptionalToNonOptional_null() {
        Source source = new Source( null, null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorNonOptionalToOptional_nonNull() {
        Source source = new Source( null, null, "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void constructorNonOptionalToOptional_null() {
        Source source = new Source( null, null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToOptional_present() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.of( "some value" ) );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void optionalToOptional_empty() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    // TODO Contentious
    // Should null Optional map to null Optional, or explicitly to empty?
    // This one is a bit stranger than the "different types" case.
    // Because here, both the source and target are the same type:
    // Optional<String> -> Optional<String>, therefore no nested mapping is required.
    // By default, the mapper just generates `target.prop = source.prop`.
    @ProcessorTest
    public void optionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isNull();
    }

    @ProcessorTest
    public void optionalToNonOptional_present() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.of( "some value" ) );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void optionalToNonOptional_empty() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void optionalToNonOptional_null() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nonOptionalToOptional_nonNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void nonOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToOptional_present() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.of( "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).contains( "some value" );
    }

    @ProcessorTest
    public void publicOptionalToOptional_empty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.empty();

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    // TODO Contentious
    // Should null Optional map to null Optional, or explicitly to empty?
    // This one is a bit stranger than the "different types" case.
    // Because here, both the source and target are the same type:
    // Optional<String> -> Optional<String>, therefore no nested mapping is required.
    // By default, the mapper just generates `target.prop = source.prop`.
    @ProcessorTest
    public void publicOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = null;

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isNull();
    }

    @ProcessorTest
    public void publicOptionalToNonOptional_present() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.of( "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void publicOptionalToNonOptional_empty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.empty();

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    public void publicOptionalToNonOptional_null() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = null;

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    public void publicNonOptionalToOptional_nonNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = "some value" ;

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).contains( "some value" );
    }

    @ProcessorTest
    public void publicNonOptionalToOptional_null() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = null ;

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }
    
}
