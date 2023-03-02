/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.differenttypes;

import java.util.Optional;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalDifferentTypesMapper.class, Source.class, Target.class
})
class OptionalDifferentTypesTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedCode() {
        generatedSource.addComparisonToFixtureFor( OptionalDifferentTypesMapper.class );
    }

    @ProcessorTest
    void constructorOptionalToOptionalWhenPresent() {
        Source source = new Source( Optional.of( new Source.SubType( "some value" ) ), null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void constructorOptionalToOptionalWhenEmpty() {
        Source source = new Source( Optional.empty(), null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void constructorOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenPresent() {
        Source source = new Source( null, Optional.of( new Source.SubType( "some value" ) ), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, Optional.empty(), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void constructorNonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void constructorNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void optionalToOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToNonOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void optionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void optionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = new Source.SubType( "some value" );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }

}
