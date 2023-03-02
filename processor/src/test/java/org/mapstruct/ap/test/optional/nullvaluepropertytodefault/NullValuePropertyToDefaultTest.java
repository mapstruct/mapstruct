/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optional.nullvaluepropertytodefault;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    NullValuePropertyToDefaultMapper.class,
    Source.class,
    Target.class
})
class NullValuePropertyToDefaultTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void generatedCode() {
        generatedSource.addComparisonToFixtureFor( NullValuePropertyToDefaultMapper.class );
    }

    @ProcessorTest
    void optionalToOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToOptional( null );

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void nonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    void publicOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.publicOptionalToOptional = null;

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    void publicNonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.publicNonOptionalToOptional = null;

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }

}
