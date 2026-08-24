/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4111;

import java.util.Collections;
import java.util.Optional;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("4111")
@WithClasses(Issue4111Mapper.class)
class Issue4111Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void emptyOptionalMapAndCollectionShouldNotThrow() {
        Issue4111Mapper.Source source = new Issue4111Mapper.Source();
        source.setName( Optional.empty() );
        source.setAttributes( Optional.empty() );
        source.setTags( Optional.empty() );

        Issue4111Mapper.Target target = Issue4111Mapper.INSTANCE.toTarget( source );

        assertThat( target.getName() ).isNull();
        assertThat( target.getAttributes() ).isNull();
        assertThat( target.getTags() ).isNull();
    }

    @ProcessorTest
    void presentOptionalMapAndCollectionShouldBeMapped() {
        Issue4111Mapper.Source source = new Issue4111Mapper.Source();
        source.setName( Optional.of( "foo" ) );
        source.setAttributes( Optional.of( Collections.singletonMap( "k", "v" ) ) );
        source.setTags( Optional.of( Collections.singletonList( "tag" ) ) );

        Issue4111Mapper.Target target = Issue4111Mapper.INSTANCE.toTarget( source );

        assertThat( target.getName() ).isEqualTo( "foo" );
        assertThat( target.getAttributes() ).containsEntry( "k", "v" );
        assertThat( target.getTags() ).containsExactly( "tag" );
    }
}
