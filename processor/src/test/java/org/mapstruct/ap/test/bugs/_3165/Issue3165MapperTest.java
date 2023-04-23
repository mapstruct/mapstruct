/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3165;

import java.util.Arrays;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses({
    Issue3165Mapper.class
})
@IssueKey("3165")
class Issue3165MapperTest {

    private Issue3165Mapper.Source src;

    @BeforeEach
    void setUp() {
        src = new Issue3165Mapper.Source(
            new String[] { "cat", "dog", "mouse" },
            Arrays.asList( "ivy", "flu", "freya" )
        );
    }

    @ProcessorTest
    void supportsAdderWhenMappingArrayToCollection() {
        Issue3165Mapper.Target target = Issue3165Mapper.INSTANCE.toTarget( src );
        Assertions.assertThat( target.getPets() ).containsExactly( "cat", "dog", "mouse" );
    }

    @ProcessorTest
    void supportsAdderWhenMappingIterableToCollection() {
        Issue3165Mapper.Target target = Issue3165Mapper.INSTANCE.toTarget( src );
        Assertions.assertThat( target.getCats() ).containsExactly( "ivy", "flu", "freya" );
    }
}
