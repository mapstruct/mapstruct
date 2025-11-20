/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jakarta.annotatewith;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.test.decorator.TestAnnotation;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJakartaInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;

/**
 * Test for the application of @AnnotateWith on decorator classes with Jakarta component model.
 */
@IssueKey("3659")
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    JakartaAnnotateWithMapper.class,
    TestAnnotation.class,
    JakartaAnnotateWithWithMapperDecorator.class
})
@WithJakartaInject
public class JakartaDecoratorAnnotateWithTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void hasCorrectImports() {
        // check the decorator
        generatedSource.forMapper( JakartaAnnotateWithMapper.class )
            .content()
            .contains( "import jakarta.inject.Inject;" )
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .contains( "@TestAnnotation" )
            .contains( "@Singleton" + lineSeparator() + "@Named" )
            .doesNotContain( "javax.inject" );
        // check the plain mapper
        generatedSource.forDecoratedMapper( JakartaAnnotateWithMapper.class ).content()
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .contains( "@Singleton" + lineSeparator() + "@Named" )
            .doesNotContain( "javax.inject" );
    }
}
