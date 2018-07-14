/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._543;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._543.dto.Source;
import org.mapstruct.ap.test.bugs._543.dto.Target;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("543")
@WithClasses({
    Issue543Mapper.class,
    Source.class,
    SourceUtil.class,
    Target.class
})
public class Issue543Test {

    @Rule
    public GeneratedSource generatedSource = new GeneratedSource();

    @Test
    public void shouldCompile() {
        generatedSource.forMapper( Issue543Mapper.class ).containsImportFor( Source.class );
    }
}
