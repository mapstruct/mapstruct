/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.versioninfo;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.WithProperties;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOptions;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * @author Andreas Gudian
 *
 */
@IssueKey( "424" )
@WithClasses( { SimpleMapper.class, WithProperties.class } )
public class VersionInfoTest {
    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @ProcessorOption( name = "mapstruct.suppressGeneratorVersionInfoComment", value = "true" )
    public void includesNoComment() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .contains( "date = \"" )
            .doesNotContain( "comments = \"version: " );
    }

    @ProcessorTest
    @ProcessorOptions( {
        @ProcessorOption( name = "mapstruct.suppressGeneratorVersionInfoComment", value = "true" ),
        @ProcessorOption( name = "mapstruct.suppressGeneratorTimestamp", value = "true" )
    } )
    public void includesNoCommentAndNoTimestamp() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .doesNotContain( "date = \"" )
            .doesNotContain( "comments = \"version: " );
    }

    @ProcessorTest
    public void includesCommentAndTimestamp() {
        generatedSource.forMapper( SimpleMapper.class ).content()
            .contains( "date = \"" )
            .contains( "comments = \"version: " );
    }

    @ProcessorTest
    @WithClasses(SuppressTimestampViaMapper.class)
    @IssueKey("2225")
    void includesNoTimestampViaMapper() {
        generatedSource.forMapper( SuppressTimestampViaMapper.class ).content()
            .doesNotContain( "date = \"" );
    }

    @ProcessorTest
    @WithClasses(SuppressTimestampViaMapperConfig.class)
    @IssueKey("2225")
    void includesNoTimestampViaMapperConfig() {
        generatedSource.forMapper( SuppressTimestampViaMapperConfig.class ).content()
            .doesNotContain( "date = \"" );
    }

}
