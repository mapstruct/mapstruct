/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    IgnoreUnmappedSourceMapper.class,
    Person.class,
    PersonDto.class
})
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey("1317")
public class IgnoreUnmappedSourcePropertiesTest {

    @Test
    public void shouldNotReportErrorOnIgnoredUnmappedSourceProperties() {

    }
}
