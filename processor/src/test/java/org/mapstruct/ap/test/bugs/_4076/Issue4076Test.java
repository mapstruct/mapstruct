/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._4076;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@IssueKey("4076")
@WithJSpecify
class Issue4076Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses(Issue4076Mapper.class)
    void shouldPropagateNullableOnParameter() {
        generatedSource.forMapper( Issue4076Mapper.class )
            .content()
            .contains( "public Target map(Source1 source1, @Nullable Source2 source2)" );
    }

    @ProcessorTest
    @WithClasses(Issue4076Mapper.class)
    void shouldPropagateNullableOnReturnType() {
        generatedSource.forMapper( Issue4076Mapper.class )
            .content()
            .contains( "public @Nullable Target mapNullableReturn(Source1 source1)" );
    }

    @ProcessorTest
    @WithClasses(Issue4076Mapper.class)
    void shouldImportNullableAnnotation() {
        generatedSource.forMapper( Issue4076Mapper.class )
            .containsImportFor( Nullable.class );
    }

    @ProcessorTest
    @WithClasses(Issue4076IterableMapper.class)
    void shouldPropagateNullableForIterableMethod() {
        generatedSource.forMapper( Issue4076IterableMapper.class )
            .content()
            .contains( "public @Nullable List<String> mapList(@Nullable List<String> source)" );
    }

    @ProcessorTest
    @WithClasses(Issue4076IterableMapper.class)
    void shouldPropagateNullableForUpdateMethod() {
        generatedSource.forMapper( Issue4076IterableMapper.class )
            .content()
            .contains( "public void update(@Nullable Source source, Target target)" );
    }
}
