/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.defaultcomponentmodel;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2277")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Source.class,
    Target.class,
})
public class DefaultComponentModelMapperTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses({
        InstanceIterableMapper.class,
        InstanceMapper.class,
        NonInstanceIterableMapper.class,
        NonInstanceMapper.class,
        NonPublicIterableMapper.class,
        NonPublicMapper.class,
    })
    public void shouldGenerateCorrectMapperInstantiation() {
        generatedSource.addComparisonToFixtureFor(
            InstanceIterableMapper.class,
            NonInstanceIterableMapper.class,
            NonPublicIterableMapper.class
        );
    }
}
