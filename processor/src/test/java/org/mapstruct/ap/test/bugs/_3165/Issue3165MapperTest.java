/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3165;

import org.assertj.core.api.Assertions;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses({
    Issue3165Mapper.class
})
@IssueKey("3165")
class Issue3165MapperTest {

    @ProcessorTest
    void supportsAdderWhenMappingArrayToCollection() {
        Issue3165Mapper.Source src = new Issue3165Mapper.Source( new String[] { "cat", "dog", "mouse" } );
        Issue3165Mapper.Target target = Issue3165Mapper.INSTANCE.toTarget( src );
        Assertions.assertThat( target.getPets() ).containsExactly( "cat", "dog", "mouse" );
    }
}
