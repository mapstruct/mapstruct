/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Tests JSpecify method-level scoping when both the source and the target types
 * are static inner classes.
 */
class JSpecifyInnerClassMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithJSpecify
    @WithClasses({ InnerClassSourceBean.class, InnerClassTargetBean.class, JSpecifyInnerClassMapper.class })
    void shouldReadAndWriteInnerClassAnnotationCorrectly() {
        generatedSource.addComparisonToFixtureFor( JSpecifyInnerClassMapper.class );
    }
}
