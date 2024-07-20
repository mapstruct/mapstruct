/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3601;

import java.util.Collections;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("3601")
class Issue3601Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @WithClasses( Issue3601Mapper.class )
    void shouldUseSourceParameterPresenceCheckCorrectly() {
        Issue3601Mapper.Target target = Issue3601Mapper.INSTANCE.map(
            new Issue3601Mapper.Source( "test1" ),
            Collections.emptyList()
        );

        assertThat( target ).isNotNull();
        assertThat( target.currentId ).isEqualTo( "test1" );
        assertThat( target.targetIds ).isNull();

        target = Issue3601Mapper.INSTANCE.map(
            null,
            Collections.emptyList()
        );

        assertThat( target ).isNull();
    }

}
