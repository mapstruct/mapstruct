/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.source.ignore;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    IgnoreUnmappedSourceMapper.class,
    Person.class,
    PersonDto.class
})
@IssueKey("1317")
public class IgnoreUnmappedSourcePropertiesTest {

    @ProcessorTest
    public void shouldNotReportErrorOnIgnoredUnmappedSourceProperties() {

    }
}
