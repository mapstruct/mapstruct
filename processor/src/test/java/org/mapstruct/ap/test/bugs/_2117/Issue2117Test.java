/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2117;

import java.nio.file.AccessMode;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2117")
@WithClasses({
    Issue2117Mapper.class
})
public class Issue2117Test {

    @ProcessorTest
    public void shouldCompile() {

        Issue2117Mapper.Target target = Issue2117Mapper.INSTANCE.toTarget( AccessMode.READ, null );

        assertThat( target ).isNotNull();
        assertThat( target.accessMode ).isEqualTo( AccessMode.READ );
    }
}
