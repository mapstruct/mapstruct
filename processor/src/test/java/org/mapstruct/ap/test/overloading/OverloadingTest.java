/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.overloading;

import java.util.Date;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses( { SourceTargetMapper.class, Source.class, Target.class, OverloadingMapperConfig.class } )
public class OverloadingTest {

    @ProcessorTest
    public void testShouldGenerateCorrectMapperImplementation() {
        SourceTargetMapper.INSTANCE.sourceToTarget( new Source( new Date() ) );
    }
}
