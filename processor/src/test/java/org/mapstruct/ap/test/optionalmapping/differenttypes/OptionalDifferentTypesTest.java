/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.differenttypes;

import java.util.Optional;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalDifferentTypesMapper.class, Source.class, Target.class
})
public class OptionalDifferentTypesTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( OptionalDifferentTypesMapper.class );

    @ProcessorTest
    public void constructorOptionalToOptionalWhenPresent() {
        Source source = new Source( Optional.of( new Source.SubType( "some value" ) ), null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void constructorOptionalToOptionalWhenEmpty() {
        Source source = new Source( Optional.empty(), null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void constructorOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void constructorOptionalToNonOptionalWhenPresent() {
        Source source = new Source( null, Optional.of( new Source.SubType( "some value" ) ), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void constructorOptionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, Optional.empty(), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void constructorNonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void constructorNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void optionalToOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToNonOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void optionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void optionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setOptionalToNonOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void nonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.setNonOptionalToOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void publicOptionalToOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToNonOptionalWhenPresent() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isEqualTo( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void publicOptionalToNonOptionalWhenEmpty() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    public void publicOptionalToNonOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicOptionalToNonOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    public void publicNonOptionalToOptionalWhenNotNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = new Source.SubType( "some value" );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).contains( new Target.SubType( "some value" ) );
    }

    @ProcessorTest
    public void publicNonOptionalToOptionalWhenNull() {
        Source source = new Source( null, null, null );
        source.publicNonOptionalToOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }

}
