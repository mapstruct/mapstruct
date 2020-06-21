/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2121;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2121")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(Issue2121Mapper.class)
public class Issue2121Test {

    @Test
    public void shouldCompile() {
        Issue2121Mapper mapper = Issue2121Mapper.INSTANCE;

        Issue2121Mapper.Target target = mapper.map( new Issue2121Mapper.Source( Issue2121Mapper.SourceEnum.VALUE1 ) );
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "VALUE1" );

        target = mapper.map( new Issue2121Mapper.Source( null ) );
        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isNull();
    }
}
