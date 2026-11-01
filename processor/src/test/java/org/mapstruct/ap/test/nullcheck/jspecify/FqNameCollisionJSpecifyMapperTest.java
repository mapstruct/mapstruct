/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.nullcheck.jspecify.dupa.Dup;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Tests JSpecify types-use annotations when the type to be annotated is referenced by its fully qualified
 * name because its simple name collides with another type's simple name. The annotation must be placed
 * before the <em>last</em> simple name segment, e.g. {@code ...dupb.@NonNull Dup}, not
 * {@code org.mapstruct.@NonNull ap.test...dupb.Dup}.
 */
class FqNameCollisionJSpecifyMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithJSpecify
    @WithClasses({ Dup.class, org.mapstruct.ap.test.nullcheck.jspecify.dupb.Dup.class,
            FqNameCollisionJSpecifyMapper.class })
    void shouldPlaceAnnotationBeforeLastFqnSegment() {
        generatedSource.addComparisonToFixtureFor( FqNameCollisionJSpecifyMapper.class );
    }
}
