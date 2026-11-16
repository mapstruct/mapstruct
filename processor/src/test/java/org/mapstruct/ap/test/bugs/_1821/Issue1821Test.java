/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1821;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@IssueKey("1797")
@WithClasses( Issue1821Mapper.class )
public class Issue1821Test {

    @ProcessorTest
    public void shouldNotGiveNullPtr() {
        Issue1821Mapper.INSTANCE.map( new Issue1821Mapper.Source( "test" ) );
    }

}
