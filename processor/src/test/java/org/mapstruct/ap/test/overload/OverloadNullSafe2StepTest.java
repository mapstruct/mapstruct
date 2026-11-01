/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overload;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <p>
 * Reproducer for the "overloaded methods produce code that does not compile" bug in
 * {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod}.
 * </p>
 * <p>
 * {@link OverloadMapper} declares two overloads of {@code map} ({@code map(OverloadA)} and {@code map(OverloadB)}),
 * each of which maps its source into an intermediate object type that is converted to a {@code String} target. Both
 * overloads produce a {@link org.mapstruct.ap.internal.model.NullSafe2StepMappingMethod} helper derived from the same
 * method name and the same target type, so both helpers are named {@code mapToString}. Because
 * {@link org.mapstruct.ap.internal.model.SupportingMappingMethod#equals} compares only the name, one of the two
 * helpers is dropped from the generated impl. The remaining call sites still reference {@code mapToString}, so the
 * generated {@code OverloadMapperImpl} does not compile.
 * </p>
 *
 * @author hduelme
 */
@WithJSpecify
@WithClasses( {
    OverloadA.class,
    OverloadB.class,
    OverloadHolderSource.class,
    OverloadHolderTarget.class,
    OverloadMapper.class
} )
class OverloadNullSafe2StepTest {

    @ProcessorTest
    void overloadedMethodsProduceCompilingCode() {
        OverloadMapper mapper = OverloadMapper.INSTANCE;

        OverloadHolderSource source = new OverloadHolderSource();
        OverloadA a = new OverloadA();
        a.setValue( 3 );
        source.setA( a );
        OverloadB b = new OverloadB();
        b.setValue( 5 );
        source.setB( b );

        OverloadHolderTarget target = mapper.mapHolder( source );

        // both overloads must resolve to their own (distinct) helper, not one dropped helper
        assertThat( target.getA() ).isEqualTo( 300L );
        assertThat( target.getB() ).isEqualTo( 500L );
    }
}
