/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conversion.precedence;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@WithClasses({ Source.class, Target.class, SourceTargetMapper.class, IntegerStringMapper.class })
public class ConversionTest {

    @ProcessorTest
    public void shouldInvokeMappingMethodInsteadOfConversion() {
        Source source = new Source();
        source.setFoo( 42 );

        Target target = SourceTargetMapper.INSTANCE.sourceToTarget( source );

        assertThat( target ).isNotNull();
        assertThat( target.getFoo() ).isEqualTo( "000042" );
    }
}
