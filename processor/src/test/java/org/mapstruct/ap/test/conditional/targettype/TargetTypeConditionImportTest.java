/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.conditional.targettype;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

class TargetTypeConditionImportTest {

    @ProcessorTest
    @WithClasses( { Source.class, Target.class, TargetTypeConditionImportMapper.class } )
    void shouldImportTargetTypeUsedByCondition() {
    }
}
