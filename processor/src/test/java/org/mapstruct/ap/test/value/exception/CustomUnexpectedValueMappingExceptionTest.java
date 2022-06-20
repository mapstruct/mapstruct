/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.value.exception;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.value.CustomIllegalArgumentException;
import org.mapstruct.ap.test.value.ExternalOrderType;
import org.mapstruct.ap.test.value.OrderType;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2169")
@WithClasses({
    Config.class,
    CustomUnexpectedValueMappingExceptionDefinedInMapper.class,
    CustomUnexpectedValueMappingExceptionDefinedInMapperAndEnumMapping.class,
    CustomUnexpectedValueMappingExceptionDefinedInMapperConfig.class,
    CustomUnexpectedValueMappingExceptionMapper.class,
    CustomIllegalArgumentException.class,
    ExternalOrderType.class,
    OrderType.class
})
public class CustomUnexpectedValueMappingExceptionTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldGenerateCustomUnexpectedValueMappingException() {
        generatedSource.addComparisonToFixtureFor(
            CustomUnexpectedValueMappingExceptionDefinedInMapper.class,
            CustomUnexpectedValueMappingExceptionDefinedInMapperAndEnumMapping.class,
            CustomUnexpectedValueMappingExceptionDefinedInMapperConfig.class,
            CustomUnexpectedValueMappingExceptionMapper.class
        );
    }
}
