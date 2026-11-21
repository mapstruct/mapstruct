/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.primitiveupdate;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    PrimitiveUpdateSource.class,
    PrimitiveUpdateTarget.class,
    PrimitiveUpdateMapper.class
})
class PrimitiveUpdateNullSafe2StepTest {

    @ProcessorTest
    void nullSourceKeepsExistingValue() {
        PrimitiveUpdateSource source = new PrimitiveUpdateSource();
        PrimitiveUpdateTarget target = new PrimitiveUpdateTarget();
        target.setNumber( 99L );

        PrimitiveUpdateMapper.INSTANCE.update( source, target );

        assertThat( target.getNumber() ).isEqualTo( 99L );
    }

    @ProcessorTest
    void nullIntermediateResultKeepsExistingValue() {
        PrimitiveUpdateSource source = new PrimitiveUpdateSource();
        source.setName( "" );
        PrimitiveUpdateTarget target = new PrimitiveUpdateTarget();
        target.setNumber( 99L );

        PrimitiveUpdateMapper.INSTANCE.update( source, target );

        assertThat( target.getNumber() ).isEqualTo( 99L );
    }

    @ProcessorTest
    void presentSourceOverwritesExistingValue() {
        PrimitiveUpdateSource source = new PrimitiveUpdateSource();
        source.setName( "42" );
        PrimitiveUpdateTarget target = new PrimitiveUpdateTarget();
        target.setNumber( 99L );

        PrimitiveUpdateMapper.INSTANCE.update( source, target );

        assertThat( target.getNumber() ).isEqualTo( 42L );
    }
}
