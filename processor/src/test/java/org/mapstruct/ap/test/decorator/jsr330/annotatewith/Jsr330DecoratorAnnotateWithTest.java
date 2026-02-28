/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jsr330.annotatewith;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.test.decorator.TestAnnotation;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

/**
 * Test for the application of @AnnotateWith on decorator classes with JSR-330 component model.
 */
@IssueKey("3659")
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    Jsr330AnnotateWithMapper.class,
    Jsr330AnnotateWithMapperDecorator.class,
    TestAnnotation.class
})
@WithJavaxInject
public class Jsr330DecoratorAnnotateWithTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldContainCustomAnnotation() {
        generatedSource.forMapper( Jsr330AnnotateWithMapper.class )
            .content()
            .contains( "@TestAnnotation" );
    }
}
