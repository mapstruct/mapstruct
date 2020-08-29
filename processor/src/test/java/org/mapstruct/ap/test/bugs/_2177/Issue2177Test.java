/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2177;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2177")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue2177Mapper.class
})
public class Issue2177Test {

    @Test
    public void shouldCorrectlyUseGenericClassesWithConstructorMapping() {

        Issue2177Mapper.Target<String> target = Issue2177Mapper.INSTANCE.map( new Issue2177Mapper.Source( "test" ) );

        assertThat( target ).isNotNull();
        assertThat( target.getValue() ).isEqualTo( "test" );
    }
}
