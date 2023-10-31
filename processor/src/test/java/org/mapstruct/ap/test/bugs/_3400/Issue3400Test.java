/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3400;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.assertions.JavaFileAssert;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@IssueKey("3400")
@WithClasses({
        Issue3400Mapper.class,
        ObjectFrom.class,
        ObjectTo.class
})
class Issue3400Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void generatedCodeShouldNotHaveUnnecessaryCastsToLong() {
        JavaFileAssert assertGeneratedMapperCode = generatedSource.forMapper( Issue3400Mapper.class );

        assertGeneratedMapperCode.content().doesNotContain( "(long)" );
        assertGeneratedMapperCode.content().contains( ".setLongField( 42L );" );
    }
}
