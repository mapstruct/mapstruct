/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.annotations;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Raimund Klein
 */
@IssueKey("990")
@WithClasses({Source.class, Target.class})
class AnnotatedMapperTest {
    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses(AnnotatedMapper.class)
    void shouldGenerateMapperImplementationWithAnnotation() {
        generatedSource.addComparisonToFixtureFor( AnnotatedMapper.class );
    }
}
