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
        Source source = new Source( Optional.of( new Source.SubType( "some value" ) ), Optional.empty(), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() )
            .hasValueSatisfying( subType ->
                assertThat( subType.getValue() ).isEqualTo( "some value" ) );
    }

    @ProcessorTest
    void constructorOptionalToOptionalWhenEmpty() {
        Source source = new Source();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenPresent() {
        Source source = new Source( Optional.empty(), Optional.of( new Source.SubType( "some value" ) ), null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        Target.SubType subType = target.getConstructorOptionalToNonOptional();
        assertThat( subType ).isNotNull();
        assertThat( subType.getValue() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void constructorOptionalToNonOptionalWhenEmpty() {
        Source source = new Source();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void constructorNonOptionalToOptionalWhenNotNull() {
        Source source = new Source( Optional.empty(), Optional.empty(), new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() )
            .hasValueSatisfying( subType ->
                assertThat( subType.getValue() ).isEqualTo( "some value" ) );
    }

    @ProcessorTest
    void constructorNonOptionalToOptionalWhenNull() {
        Source source = new Source();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getConstructorNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToOptionalWhenPresent() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() )
            .hasValueSatisfying( subType ->
                assertThat( subType.getValue() ).isEqualTo( "some value" ) );
    }

    @ProcessorTest
    void optionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void optionalToNonOptionalWhenPresent() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.SubType( "some value" ) ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        Target.SubType subType = target.getOptionalToNonOptional();
        assertThat( subType ).isNotNull();
        assertThat( subType.getValue() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void optionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNotNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() )
            .hasValueSatisfying( subType ->
                assertThat( subType.getValue() ).isEqualTo( "some value" ) );
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenPresent() {
        Source source = new Source();
        source.publicOptionalToOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional )
            .hasValueSatisfying( subType ->
                assertThat( subType.getValue() ).isEqualTo( "some value" ) );
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.publicOptionalToOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenPresent() {
        Source source = new Source();
        source.publicOptionalToNonOptional = Optional.of( new Source.SubType( "some value" ) );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        Target.SubType subType = target.publicOptionalToNonOptional;
        assertThat( subType ).isNotNull();
        assertThat( subType.getValue() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void publicOptionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.publicOptionalToNonOptional = Optional.empty();

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicOptionalToNonOptional ).isNull();
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNotNull() {
        Source source = new Source();
        source.publicNonOptionalToOptional = new Source.SubType( "some value" );

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional )
            .hasValueSatisfying( subType ->
                assertThat( subType.getValue() ).isEqualTo( "some value" ) );
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.publicNonOptionalToOptional = null;

        Target target = OptionalDifferentTypesMapper.INSTANCE.toTarget( source );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }

}
