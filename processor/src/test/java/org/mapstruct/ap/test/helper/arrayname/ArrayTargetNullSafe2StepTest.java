/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.helper.arrayname;

import org.mapstruct.ap.test.helper.arrayname.ArrayTargetNullSafe2StepMapper.Input;
import org.mapstruct.ap.test.helper.arrayname.ArrayTargetNullSafe2StepMapper.Source;
import org.mapstruct.ap.test.helper.arrayname.ArrayTargetNullSafe2StepMapper.Target;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * The name of the null-safe two-step helper has to be a valid identifier when the target type is an array.
 */
@WithJSpecify
@WithClasses( ArrayTargetNullSafe2StepMapper.class )
class ArrayTargetNullSafe2StepTest {

    @ProcessorTest
    void nullSafe2StepWithArrayTarget() {
        Target target = ArrayTargetNullSafe2StepMapper.INSTANCE.map( new Source( new Input( "a,b" ) ) );

        assertThat( target.getValues() ).containsExactly( "a", "b" );

        target = ArrayTargetNullSafe2StepMapper.INSTANCE.map( new Source( new Input( null ) ) );

        assertThat( target.getValues() ).isNull();
    }
}
