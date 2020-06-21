/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2117;

import java.nio.file.AccessMode;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2117")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue2117Mapper.class
})
public class Issue2117Test {

    @Test
    public void shouldCompile() {

        Issue2117Mapper.Target target = Issue2117Mapper.INSTANCE.toTarget( AccessMode.READ, null );

        assertThat( target ).isNotNull();
        assertThat( target.accessMode ).isEqualTo( AccessMode.READ );
    }
}
