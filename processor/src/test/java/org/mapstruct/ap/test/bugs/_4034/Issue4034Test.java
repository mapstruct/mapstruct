/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4034;

import java.util.Arrays;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for issue 4034: Cache {@link org.mapstruct.ap.internal.model.common.Type} instances in
 * {@link org.mapstruct.ap.internal.model.common.TypeFactory#getType} to avoid redundant work.
 *
 * @author Ankita Advitot
 */
@IssueKey("4034")
@WithClasses(Issue4034Mapper.class)
public class Issue4034Test {

    @ProcessorTest
    void shouldCompileMapperWithCachedTypes() {
        Issue4034Mapper.Source source = new Issue4034Mapper.Source();
        source.setName( "test" );
        source.setDescription( "desc" );
        source.setTags( Arrays.asList( "a", "b" ) );
        source.setCounts( Arrays.asList( 1, 2, 3 ) );

        Issue4034Mapper.Target target = Issue4034Mapper.INSTANCE.map( source );

        assertThat( target ).isNotNull();
        assertThat( target.getName() ).isEqualTo( "test" );
        assertThat( target.getDescription() ).isEqualTo( "desc" );
        assertThat( target.getTags() ).containsExactly( "a", "b" );
        assertThat( target.getCounts() ).containsExactly( 1, 2, 3 );
    }
}
