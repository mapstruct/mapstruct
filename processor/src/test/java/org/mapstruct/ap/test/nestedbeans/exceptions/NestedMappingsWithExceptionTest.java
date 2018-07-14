/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans.exceptions;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.nestedbeans.exceptions._target.DeveloperDto;
import org.mapstruct.ap.test.nestedbeans.exceptions._target.ProjectDto;
import org.mapstruct.ap.test.nestedbeans.exceptions.source.Developer;
import org.mapstruct.ap.test.nestedbeans.exceptions.source.Project;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    DeveloperDto.class,
    ProjectDto.class,
    Developer.class,
    Project.class,
    ProjectMapper.class,
    MappingException.class,
    EntityFactory.class
})
@IssueKey("1304")
@RunWith(AnnotationProcessorTestRunner.class)
public class NestedMappingsWithExceptionTest {

    @Test
    public void shouldGenerateCodeThatCompiles() {

    }
}
