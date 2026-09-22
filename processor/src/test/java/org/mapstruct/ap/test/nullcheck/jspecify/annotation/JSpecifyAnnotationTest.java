/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.annotation;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleSource;
import org.mapstruct.ap.test.nullcheck.jspecify.SimpleTarget;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.WithPackageInfo;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithJSpecify
@WithClasses({SimpleSource.class, SimpleTarget.class})
public class JSpecifyAnnotationTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationMethodeMapper.class )
    void checkMethodeAnnotations() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationMethodeMapper.class );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationMethodeMapper.class )
    @WithPackageInfo( JSpecifyAnnotationMethodeMapper.class )
    void checkMethodeAnnotationsInNullMarkedTargetPackage() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationMethodeMapper.class, "InNullMarkedTargetPackage" );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationScopeMethodeMapper.class )
    void checkScopeMethodeAnnotations() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationScopeMethodeMapper.class );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationScopeIgnoredMapper.class )
    void checkScopeIgnoredAnnotations() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationScopeIgnoredMapper.class );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationScopeIgnoredMapper.class )
    @WithPackageInfo( JSpecifyAnnotationScopeIgnoredMapper.class )
    void checkScopeIgnoredAnnotationsInNullMarkedTargetPackage() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationScopeIgnoredMapper.class,
                "InNullMarkedTargetPackage" );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationIgnoredMapper.class )
    void checkIgnoredAnnotations() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationIgnoredMapper.class );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationPackageScopeClassMapper.class )
    @WithPackageInfo( JSpecifyAnnotationPackageScopeClassMapper.class )
    void checkPackageScopeClassAnnotations() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationPackageScopeClassMapper.class,
            "InNullMarkedTargetPackage" );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationPackageScopeMethodMapper.class )
    void checkPackageScopeMethodAnnotationsInPlainPackage() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationPackageScopeMethodMapper.class );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationPackageScopeMethodMapper.class )
    @WithPackageInfo( JSpecifyAnnotationPackageScopeMethodMapper.class )
    void checkPackageScopeMethodAnnotations() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationPackageScopeMethodMapper.class,
            "InNullMarkedTargetPackage" );
    }

    @ProcessorTest
    @WithClasses( JSpecifyAnnotationClassScopeMethodMapper.class )
    void checkClassScopeMethodAnnotations() {
        generatedSource.addComparisonToFixtureFor( JSpecifyAnnotationClassScopeMethodMapper.class );
    }
}
