/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JodaTimeBuildInSource;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.JodaTimeBuildInTarget;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJSpecify;
import org.mapstruct.ap.testutil.WithJavaxJaxb;
import org.mapstruct.ap.testutil.WithJoda;
import org.mapstruct.ap.testutil.WithPackageInfo;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

@WithJoda
@WithJavaxJaxb
@WithJSpecify
@WithClasses( {JodaTimeBuildInTarget.class, JodaTimeBuildInSource.class, JodaTimeBuildInMapper.class } )
public class JspecifyJodaTimeBuildInTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithPackageInfo( JodaTimeBuildInSource.class )
    public void testJspecifyBuildInSourceNoneNullableTargetNullable() {
        generatedSource.addComparisonToFixtureFor( JodaTimeBuildInMapper.class, "SourceNoneNullableTargetNullable" );
    }

    @ProcessorTest
    @WithPackageInfo( { JodaTimeBuildInTarget.class, JodaTimeBuildInSource.class } )
    public void testJspecifyBuildInSourceNoneNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JodaTimeBuildInMapper.class,
                "SourceNoneNullableTargetNoneNullable" );
    }

    @ProcessorTest
    @WithPackageInfo( JodaTimeBuildInTarget.class )
    public void testJspecifyBuildInSourceNullableTargetNoneNullable() {
        generatedSource.addComparisonToFixtureFor( JodaTimeBuildInMapper.class, "SourceNullableTargetNoneNullable" );
    }

}
