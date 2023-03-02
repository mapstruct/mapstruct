/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullcheckalways;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    OptionalNullCheckAlwaysMapper.class, Source.class, Target.class
})
class OptionalNullCheckAlwaysTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedCode() {
        generatedSource.addComparisonToFixtureFor( OptionalNullCheckAlwaysMapper.class );
    }

    @ProcessorTest
    void optionalToOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToOptional( null );

        Target target = OptionalNullCheckAlwaysMapper.INSTANCE.toTarget( source );
        assertThat( target.isOptionalToOptionalCalled() ).isFalse();
    }

    @ProcessorTest
    void optionalToNonOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToNonOptional( null );

        Target target = OptionalNullCheckAlwaysMapper.INSTANCE.toTarget( source );
        assertThat( target.isOptionalToNonOptionalCalled() ).isFalse();
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = OptionalNullCheckAlwaysMapper.INSTANCE.toTarget( source );
        assertThat( target.isNonOptionalToOptionalCalled() ).isFalse();
    }

}
