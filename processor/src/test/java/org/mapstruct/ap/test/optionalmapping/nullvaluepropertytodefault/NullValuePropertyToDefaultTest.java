/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.optionalmapping.nullvaluepropertytodefault;

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
public class NullValuePropertyToDefaultTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor( NullValuePropertyToDefaultMapper.class );

    @ProcessorTest
    public void optionalToOptionalWhenNull() {
        Source source = new Source();
        source.setOptionalToOptional( null );

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.getOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void nonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.setNonOptionalToOptional( null );

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.getNonOptionalToOptional() ).isEmpty();
    }

    @ProcessorTest
    public void publicOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.publicOptionalToOptional = null;

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.publicOptionalToOptional ).isEmpty();
    }

    @ProcessorTest
    public void publicNonOptionalToOptionalWhenNull() {
        Source source = new Source();
        source.publicNonOptionalToOptional = null;

        Target target = new Target();
        NullValuePropertyToDefaultMapper.INSTANCE.mapTarget( source, target );
        assertThat( target.publicNonOptionalToOptional ).isEmpty();
    }

}
