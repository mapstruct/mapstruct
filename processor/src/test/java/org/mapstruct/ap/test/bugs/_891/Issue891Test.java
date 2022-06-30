/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._891;

import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Sjaak Derksen
 */
@WithClasses({BuggyMapper.class, Dest.class, Src.class, SrcNested.class})
public class Issue891Test {

    @ProcessorTest
    public void shouldNotThrowNPE() {

        BuggyMapper.INSTANCE.convert( new Src() );
    }
}
