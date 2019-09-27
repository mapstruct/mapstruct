/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.builder.off;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.factory.Mappers;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1743")
@WithClasses({
    SimpleMutablePerson.class,
    SimpleNotRealyImmutablePerson.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class SimpleNotRealyImmutableBuilderTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource();

    @Test
    @WithClasses({ SimpleMapper.class })
    public void testSimpleImmutableBuilderHappyPath() {
        SimpleMapper mapper = Mappers.getMapper( SimpleMapper.class );
        SimpleMutablePerson source = new SimpleMutablePerson();
        source.setFullName( "Bob" );

        SimpleNotRealyImmutablePerson targetObject = mapper.toNotRealyImmutable( source );

        assertThat( targetObject.getName() ).isEqualTo( "Bob" );

    }
}
