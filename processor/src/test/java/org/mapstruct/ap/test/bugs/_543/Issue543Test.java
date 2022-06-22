/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._543;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.bugs._543.dto.Source;
import org.mapstruct.ap.test.bugs._543.dto.Target;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("543")
@WithClasses({
    Issue543Mapper.class,
    Source.class,
    SourceUtil.class,
    Target.class
})
public class Issue543Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldCompile() {
        generatedSource.forMapper( Issue543Mapper.class ).containsImportFor( Source.class );
    }
}
