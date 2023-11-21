/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nested;

import java.util.Optional;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalNestedMapper.class, Source.class, Target.class
})
public class OptionalNestedTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource()
        .addComparisonToFixtureFor( OptionalNestedMapper.class );

    @ProcessorTest
    public void optionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void optionalToNonOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToNonOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedOptionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( Optional.empty() ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedOptionalToNonOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedOptionalToNonOptionalWhenNotNull() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( Optional.of( "some value" ) ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void optionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void optionalToOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedOptionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( Optional.empty() ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedOptionalToOptionalWhenNotNull() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( Optional.of( "some value" ) ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    public void nonOptionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nonOptionalToNonOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedNonOptionalToNonOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.of( new Source.NestedNonOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    public void nestedNonOptionalToNonOptionalWhenNotNull() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.of( new Source.NestedNonOptional( "some value" ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    public void nonOptionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedNonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.of( new Source.NestedNonOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nestedNonOptionalToOptionalWhenNotNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.of( new Source.NestedNonOptional( "some value" ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( "some value" );
    }

}
