/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2301;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2301")
@WithClasses({
    Artifact.class,
    ArtifactDto.class,
    Issue2301Mapper.class
})
public class Issue2301Test {

    @ProcessorTest
    public void shouldCorrectlyIgnoreProperties() {
        Artifact artifact = Issue2301Mapper.INSTANCE.map( new ArtifactDto( "mapstruct" ) );

        assertThat( artifact ).isNotNull();
        assertThat( artifact.getName() ).isEqualTo( "mapstruct" );
        assertThat( artifact.getDependantBuildRecords() ).isNull();

        Issue2301Mapper.INSTANCE.update( artifact, new ArtifactDto( "mapstruct-processor" ) );

        assertThat( artifact.getName() ).isEqualTo( "mapstruct-processor" );
    }
}
