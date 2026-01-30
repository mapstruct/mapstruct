/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.decorator.jsr330;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.decorator.Address;
import org.mapstruct.ap.test.decorator.AddressDto;
import org.mapstruct.ap.test.decorator.Person;
import org.mapstruct.ap.test.decorator.PersonDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;

/**
 * Test for the application of decorators using component model jsr330.
 *
 * @author Andreas Gudian
 */
@WithClasses({
    Person.class,
    PersonDto.class,
    Address.class,
    AddressDto.class,
    PersonMapper.class,
    PersonMapperDecorator.class
})
@IssueKey("592")
@WithJavaxInject
public class Jsr330DecoratorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @IssueKey("664")
    @ProcessorTest
    public void hasSingletonAnnotation() {
        // check the decorator
        generatedSource.forMapper( PersonMapper.class ).content()
                       .contains( "@Singleton" + lineSeparator() + "@Named" );
        // check the plain mapper
        generatedSource.forDecoratedMapper( PersonMapper.class ).content()
                       .contains( "@Singleton" + lineSeparator() + "@Named" );
    }
}
