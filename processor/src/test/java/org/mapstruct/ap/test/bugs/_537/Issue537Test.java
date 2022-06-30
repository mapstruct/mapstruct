/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._537;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Christian Bandowski
 */
@IssueKey("537")
@WithClasses({
    Issue537Mapper.class,
    Issue537MapperConfig.class,
    ReferenceMapper.class,
    Source.class,
    Target.class
})
public class Issue537Test {

    @ProcessorTest
    public void testThatReferencedMapperWillBeUsed() {
        Target target = Issue537Mapper.INSTANCE.mapDto( new Source( "abc" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( 3 );
    }
}
