/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.versioninfo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOptions;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Andreas Gudian
 *
 */
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey( "424" )
@WithClasses( SimpleMapper.class )
public class VersionInfoTest {
    private final GeneratedSource generatedSource = new GeneratedSource();

    @Rule
    public GeneratedSource getGeneratedSource() {
        return generatedSource;
    }

    @Test
    @ProcessorOption( name = "mapstruct.suppressGeneratorVersionInfoComment", value = "true" )
    public void includesNoComment() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .contains( "date = \"" )
            .doesNotContain( "comments = \"version: " );
    }

    @Test
    @ProcessorOptions( {
        @ProcessorOption( name = "mapstruct.suppressGeneratorVersionInfoComment", value = "true" ),
        @ProcessorOption( name = "mapstruct.suppressGeneratorTimestamp", value = "true" )
    } )
    public void includesNoCommentAndNoTimestamp() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .doesNotContain( "date = \"" )
            .doesNotContain( "comments = \"version: " );
    }

    @Test
    public void includesCommentAndTimestamp() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .contains( "date = \"" )
            .contains( "comments = \"version: " );
    }

}
