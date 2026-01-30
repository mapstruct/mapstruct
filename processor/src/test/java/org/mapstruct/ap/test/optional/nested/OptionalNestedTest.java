/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nested;

import java.util.Optional;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Artist.class,
    OptionalNestedMapper.class,
    OptionalNestedPresenceCheckFirstMapper.class,
    OptionalNestedPresenceCheckMapper.class,
    Source.class,
    Target.class,
    TargetAggregate.class,
})
class OptionalNestedTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedCode() {
        generatedSource.addComparisonToFixtureFor(
            OptionalNestedMapper.class,
            OptionalNestedPresenceCheckFirstMapper.class,
            OptionalNestedPresenceCheckMapper.class
        );
    }

    @ProcessorTest
    void optionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nestedOptionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( Optional.empty() ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nestedOptionalToNonOptionalWhenNotNull() {
        Source source = new Source();
        source.setOptionalToNonOptional( Optional.of( new Source.NestedOptional( Optional.of( "some value" ) ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void optionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void nestedOptionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( Optional.empty() ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void nestedOptionalToOptionalWhenNotNull() {
        Source source = new Source();
        source.setOptionalToOptional( Optional.of( new Source.NestedOptional( Optional.of( "some value" ) ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).contains( "some value" );
    }

    @ProcessorTest
    void nonOptionalToNonOptionalWhenEmpty() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nestedNonOptionalToNonOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.of( new Source.NestedNonOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isNull();
    }

    @ProcessorTest
    void nestedNonOptionalToNonOptionalWhenNotNull() {
        Source source = new Source();
        source.setNonOptionalToNonOptional( Optional.of( new Source.NestedNonOptional( "some value" ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToNonOptional() ).isEqualTo( "some value" );
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenEmpty() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.empty() );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void nestedNonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.of( new Source.NestedNonOptional( null ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void nestedNonOptionalToOptionalWhenNotNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( Optional.of( new Source.NestedNonOptional( "some value" ) ) );

        Target target = OptionalNestedMapper.INSTANCE.toTarget( source );
        assertThat( target.getNonOptionalToOptional() ).contains( "some value" );
    }

}
