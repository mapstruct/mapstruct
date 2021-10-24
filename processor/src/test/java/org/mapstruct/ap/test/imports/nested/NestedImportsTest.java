/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.imports.nested;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.imports.nested.other.SourceInOtherPackage;
import org.mapstruct.ap.test.imports.nested.other.TargetInOtherPackage;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    SourceInOtherPackage.class,
    TargetInOtherPackage.class,
    Source.class,
    Target.class
})
@IssueKey("1386,148")
class NestedImportsTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( {
        SourceInOtherPackageMapper.class
    } )
    void shouldGenerateNestedInnerClassesForSourceInOtherPackage() {

        generatedSource.addComparisonToFixtureFor( SourceInOtherPackageMapper.class );
    }

    @ProcessorTest
    @WithClasses( {
        TargetInOtherPackageMapper.class
    } )
    void shouldGenerateNestedInnerClassesForTargetInOtherPackage() {

        generatedSource.addComparisonToFixtureFor( TargetInOtherPackageMapper.class );
    }
}
