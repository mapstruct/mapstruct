/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.exception;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2169")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Config.class,
    CustomDefaultExceptionDefinedInMapper.class,
    CustomDefaultExceptionDefinedInMapperAndEnumMapping.class,
    CustomDefaultExceptionDefinedInMapperConfig.class,
    CustomDefaultExceptionMapper.class,
    CustomIllegalArgumentException.class,
    ExternalOrderType.class,
    OrderType.class
})
public class CustomDefaultExceptionTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    public void shouldGenerateCustomDefaultException() {
        generatedSource.addComparisonToFixtureFor(
            CustomDefaultExceptionDefinedInMapper.class,
            CustomDefaultExceptionDefinedInMapperAndEnumMapping.class,
            CustomDefaultExceptionDefinedInMapperConfig.class,
            CustomDefaultExceptionMapper.class
        );
    }
}
