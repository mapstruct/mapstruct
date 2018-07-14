/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedsource.exceptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 *
 * @author Richard Lea <chigix@zoho.com>
 */
@WithClasses({
    Bucket.class,
    User.class,
    Resource.class,
    ResourceDto.class,
    NoSuchUser.class,
    ResourceMapper.class
})
@IssueKey("1332")
@RunWith(AnnotationProcessorTestRunner.class)
public class NestedExceptionTest {

    @Test
    public void shouldGenerateCodeThatCompiles() {

    }
}
