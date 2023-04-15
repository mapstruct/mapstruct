/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3163;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses({
    ASource.class, ATarget.class, BSource.class, BTarget.class,
    ImmutableASource.class, ImmutableATarget.class,
    ImmutableBSource.class, ImmutableBTarget.class,
    Mapper.class, MapStructConfiguration.class
})
public class Issue3163Test {

    @ProcessorTest
    public void shouldCompile() {
    }
}
