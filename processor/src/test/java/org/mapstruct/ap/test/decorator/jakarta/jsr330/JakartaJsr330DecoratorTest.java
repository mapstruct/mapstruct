/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jakarta.jsr330;

import static java.lang.System.lineSeparator;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithJakartaInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    PersonMapper.class,
    PersonMapperDecorator.class
})
@IssueKey("2567")
@WithJakartaInject
// We can't use Spring for testing since Spring 5 does not support Jakarta Inject
// However, we can test the generated source code
public class JakartaJsr330DecoratorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void hasCorrectImports() {
        // check the decorator
        generatedSource.forMapper( PersonMapper.class )
            .content()
            .contains( "import jakarta.inject.Inject;" )
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .contains( "@Singleton" + lineSeparator() + "@Named" )
            .doesNotContain( "javax.inject" );
        // check the plain mapper
        generatedSource.forDecoratedMapper( PersonMapper.class ).content()
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .contains( "@Singleton" + lineSeparator() + "@Named" )
            .doesNotContain( "javax.inject" );
    }
}
