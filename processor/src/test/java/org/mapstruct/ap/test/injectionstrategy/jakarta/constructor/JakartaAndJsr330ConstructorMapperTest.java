/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jakarta.constructor;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJakartaInject;
import org.mapstruct.ap.testutil.WithJavaxInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2567")
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerJakartaConstructorMapper.class,
    GenderJakartaConstructorMapper.class,
    ConstructorJakartaConfig.class
})
@ComponentScan(basePackageClasses = CustomerJakartaConstructorMapper.class)
@Configuration
@WithJakartaInject
@WithJavaxInject
@Tag("spring")
public class JakartaAndJsr330ConstructorMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveJakartaInjection() {
        generatedSource.forMapper( CustomerJakartaConstructorMapper.class )
            .content()
            .contains( "import jakarta.inject.Inject;" )
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .contains( "private final GenderJakartaConstructorMapper" )
            .contains( "@Inject" + lineSeparator() +
                "    public CustomerJakartaConstructorMapperImpl(GenderJakartaConstructorMapper" )
            .doesNotContain( "javax.inject" );
    }
}
