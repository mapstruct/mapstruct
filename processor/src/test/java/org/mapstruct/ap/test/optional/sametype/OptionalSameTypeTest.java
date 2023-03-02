/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.sametype;

import java.util.Optional;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalSameTypeMapper.class, Source.class, Target.class
})
class OptionalSameTypeTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedCode() {
        generatedSource.addComparisonToFixtureFor( OptionalSameTypeMapper.class );
    }

    @ProcessorTest
    void constructorOptionalToOptionalWhenPresent() {
        Source source = new Source( Optional.of( "some value" ), null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    void constructorOptionalToOptionalWhenEmpty() {
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
    void constructorOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isNull();
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenPresent() {
        Source source = new Source( null, Optional.of( "some value" ), null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, Optional.empty(), null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void constructorNonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    void constructorNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.of( "some value" ) );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    void optionalToOptionalWhenEmpty() {
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
    void optionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isNull();
    }

    @ProcessorTest
    void optionalToNonOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.of( "some value" ) );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void optionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void optionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.of( "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).contains( "some value" );
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenEmpty() {
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
    void publicOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = null;

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isNull();
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.of( "some value" );

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.empty();

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = null;

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = "some value";

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).contains( "some value" );
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = null;

        Target target = OptionalSameTypeMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }

}
