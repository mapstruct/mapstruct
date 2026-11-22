/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.reuse;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@WithJSpecify
@WithClasses({
    ReuseSource.class,
    ReuseTarget.class,
    Wrapper.class,
    Value.class,
    ReuseMapper.class
})
class ReuseNullSafe2StepTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void bothPropertiesAreMappedThroughTheSameTwoSteps() {
        ReuseSource source = new ReuseSource();
        source.setName( "hello" );

        ReuseTarget target = ReuseMapper.INSTANCE.map( source );

        assertThat( target.getValueA().getValue() ).isEqualTo( "hello" );
        assertThat( target.getValueA().getFactor() ).isEqualTo( 42 );
        assertThat( target.getValueB().getValue() ).isEqualTo( "hello" );
        assertThat( target.getValueB().getFactor() ).isEqualTo( 42 );
    }

    @ProcessorTest
    void nullNameIsMappedToNull() {
        ReuseSource source = new ReuseSource();

        ReuseTarget target = ReuseMapper.INSTANCE.map( source );

        assertThat( target.getValueA() ).isNull();
        assertThat( target.getValueB() ).isNull();
    }

    @ProcessorTest
    void formatsNotPartOfTheBodyShareOneHelper() {
        generatedSource.forMapper( ReuseMapper.class )
            .content()
            .containsOnlyOnce( "private Value wrapToValue(" )
            .doesNotContain( "wrapToValue1" );
    }
}
