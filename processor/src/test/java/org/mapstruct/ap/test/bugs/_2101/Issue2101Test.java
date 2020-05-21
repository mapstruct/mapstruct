/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2101;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("2101")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(Issue2101Mapper.class)
public class Issue2101Test {

    @Test
    public void shouldMap() {

        Issue2101Mapper.Source source = new Issue2101Mapper.Source();
        source.value1 = "v1";
        source.value2 = "v2";

        Issue2101Mapper.Target target = Issue2101Mapper.INSTANCE.map( source );

        assertThat( target.codeValue1.code ).isEqualTo( "c1" );
        assertThat( target.codeValue1.value ).isEqualTo( "v1" );
        assertThat( target.codeValue2.code ).isEqualTo( "c2" );
        assertThat( target.codeValue2.value ).isEqualTo( "v2" );

    }
}
