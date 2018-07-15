/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1339;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    Issue1339Mapper.class,
    Callback.class
})
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1339")
public class Issue1339Test {

    @Test
    public void shouldCompile() {
        Issue1339Mapper.Source source = new Issue1339Mapper.Source();
        source.field = "test";
        Issue1339Mapper.Target target = Issue1339Mapper.INSTANCE.map( source, 10, 50 );

        assertThat( target.otherField ).isEqualTo( 50 );
        assertThat( target.field ).isEqualTo( "test" );
    }
}
