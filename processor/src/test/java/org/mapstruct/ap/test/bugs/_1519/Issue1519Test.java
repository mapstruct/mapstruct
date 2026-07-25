/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1519;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@IssueKey("1519")
@WithClasses({
    Issue1519Mapper.class,
    Issue1519DefaultConstructorMapper.class
})
class Issue1519Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    void shouldNotGenerateUnusedDelegateField() {
        generatedSource.forMapper( Issue1519Mapper.class ).content()
            .contains( "super( delegate );" )
            .doesNotContain( "Issue1519Mapper delegate;" )
            .doesNotContain( "this.delegate = delegate;" );
    }

    @ProcessorTest
    void shouldNotCreateDelegateForDefaultConstructorDecorator() {
        generatedSource.forJavaFile(
            "org/mapstruct/ap/test/bugs/_1519/dest/Issue1519DefaultConstructorMapperImpl.java"
        ).content()
            .contains( "public Issue1519DefaultConstructorMapperImpl() {\n    }" )
            .doesNotContain( "delegate" )
            .doesNotContain( "Issue1519DefaultConstructorMapperImpl_" )
            .doesNotContain( "import org.mapstruct.ap.test.bugs._1519.Issue1519DefaultConstructorMapper;" );
    }
}
