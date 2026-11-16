/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test for the application of @AnnotatedWith on decorator classes.
 */
@IssueKey("3659")
@WithClasses({
    TestAnnotation.class,
    AnnotatedMapper.class,
    AnnotatedMapperDecorator.class
})
public class DecoratedWithAnnotatedWithTest {

    @ProcessorTest
    public void shouldApplyAnnotationFromDecorator() {
        Class<?> implementationClass = AnnotatedMapper.INSTANCE.getClass();

        assertThat( implementationClass ).hasAnnotation( TestAnnotation.class );
        assertThat( implementationClass.getAnnotation( TestAnnotation.class ).value() ).isEqualTo( "decoratorValue" );
    }
}
