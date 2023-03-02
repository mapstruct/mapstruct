/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for mapping logic from {@link org.mapstruct.ap.internal.model.OptionalMappingMethod}.
 *
 * @author Ken Wang
 */
@WithClasses({
        OptionalTestMapper.class,
        Source.class,
        Target.class
})
public class OptionalMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( OptionalTestMapper.class );

    @ProcessorTest
    public void testMappingSimpleOptionalToNonOptional() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( "some value" ) );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void testMappingEmptySimpleOptionalToNonOptional() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromNonOptional() {
        Target target = new Target();
        target.setOptionalToNonOptional( "some value" );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalToNonOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromNullNonOptional() {
        Target target = new Target();
        target.setOptionalToNonOptional( null );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalToNonOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testMappingNonOptionalToSimpleOptional() {
        Source source = new Source();
        source.setNonOptionalToOptional( "some value" );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getNonOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testMappingNullNonOptionalToSimpleOptional() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromSimpleOptional() {
        Target target = new Target();
        target.setNonOptionalToOptional( Optional.of( "some value" ) );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getNonOptionalToOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromEmptySimpleOptional() {
        Target target = new Target();
        target.setNonOptionalToOptional( Optional.empty() );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getNonOptionalToOptional() ).isNull();
    }
    
    @ProcessorTest
    public void testMappingSimpleOptionalToOptional() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( "some value" ) );
        
        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testMappingEmptySimpleOptionalToOptional() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromOptional() {
        Target target = new Target();
        target.setOptionalToOptional( Optional.of( "some value" ) );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void testReverseMappingSimpleOptionalFromEmptyOptional() {
        Target target = new Target();
        target.setOptionalToOptional( Optional.empty() );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalToOptional() ).isEmpty();
    }
    
    @ProcessorTest
    public void testMappingSubTypeOptionalToNonOptional() {
        Source source = new Source();
        source.setOptionalSubTypeToNonOptional( Optional.of( new Source.SubType( 94, "test" ) ) );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalSubTypeToNonOptional() ).isEqualTo( new Target.SubType( 94, "test" ) );
    }

    @ProcessorTest
    public void testMappingEmptySubTypeOptionalToNonOptional() {
        Source source = new Source();
        source.setOptionalSubTypeToNonOptional( Optional.empty() );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalSubTypeToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromNonOptional() {
        Target target = new Target();
        target.setOptionalSubTypeToNonOptional( new Target.SubType( 94, "test" ) );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalSubTypeToNonOptional() ).contains( new Source.SubType( 94, "test" ) );
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromNullNonOptional() {
        Target target = new Target();
        target.setOptionalSubTypeToNonOptional( null );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalSubTypeToNonOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testMappingNonOptionalToSubTypeOptional() {
        Source source = new Source();
        source.setNonOptionalSubTypeToOptional( new Source.SubType( 94, "test" ) );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getNonOptionalSubTypeToOptional() ).contains( new Target.SubType( 94, "test" ) );
    }

    @ProcessorTest
    public void testMappingNullNonOptionalToSubTypeOptional() {
        Source source = new Source();
        source.setNonOptionalSubTypeToOptional( null );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getNonOptionalSubTypeToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromSubTypeOptional() {
        Target target = new Target();
        target.setNonOptionalSubTypeToOptional( Optional.of( new Target.SubType( 94, "test" ) ) );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getNonOptionalSubTypeToOptional() ).isEqualTo( new Source.SubType( 94, "test" ) );
    }

    @ProcessorTest
    public void testReverseMappingNonOptionalFromEmptySubTypeOptional() {
        Target target = new Target();
        target.setNonOptionalSubTypeToOptional( Optional.empty() );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getNonOptionalSubTypeToOptional() ).isNull();
    }

    @ProcessorTest
    public void testMappingSubTypeOptionalToOptional() {
        Source source = new Source();
        source.setOptionalSubTypeToOptional( Optional.of( new Source.SubType( 94, "test" ) ) );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalSubTypeToOptional() ).contains( new Target.SubType( 94, "test" ) );
    }

    @ProcessorTest
    public void testMappingEmptySubTypeOptionalToOptional() {
        Source source = new Source();
        source.setOptionalSubTypeToOptional( Optional.empty() );

        Target target = OptionalTestMapper.INSTANCE.map( source );
        assertThat( target.getOptionalSubTypeToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromOptional() {
        Target target = new Target();
        target.setOptionalSubTypeToOptional( Optional.of( new Target.SubType( 94, "test" ) ) );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalSubTypeToOptional() ).contains( new Source.SubType( 94, "test" ) );
    }

    @ProcessorTest
    public void testReverseMappingSubTypeOptionalFromEmptyOptional() {
        Target target = new Target();
        target.setOptionalSubTypeToOptional( Optional.empty() );

        Source source = OptionalTestMapper.INSTANCE.map( target );
        assertThat( source.getOptionalSubTypeToOptional() ).isEmpty();
    }

}
