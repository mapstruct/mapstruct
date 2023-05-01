/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3165;

import java.util.Arrays;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    Issue3165Mapper.class
})
@IssueKey("3165")
class Issue3165MapperTest {

    @ProcessorTest
    void supportsAdderWhenMappingArrayAndIterableToCollection() {
        Issue3165Mapper.Source src = new Issue3165Mapper.Source(
            new String[] { "cat", "dog", "mouse" },
            Arrays.asList( "ivy", "flu", "freya" )
        );
        Issue3165Mapper.Target target = Issue3165Mapper.INSTANCE.toTarget( src );
        assertThat( target.getPets() ).containsExactly( "cat", "dog", "mouse" );
        assertThat( target.getCats() ).containsExactly( "ivy", "flu", "freya" );
    }
}
