/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jakarta.setter;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJakartaInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerJakartaSetterMapper.class,
    GenderJakartaSetterMapper.class,
    SetterJakartaConfig.class
})
@IssueKey("3229")
@Configuration
@WithJakartaInject
public class JakartaSetterMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveSetterInjection() {
        String method = "@Inject" + lineSeparator() +
            "    public void setGenderJakartaSetterMapper(GenderJakartaSetterMapper genderJakartaSetterMapper) {" +
            lineSeparator() + "        this.genderJakartaSetterMapper = genderJakartaSetterMapper;" +
            lineSeparator() + "    }";
        generatedSource.forMapper( CustomerJakartaSetterMapper.class )
            .content()
            .contains( "import jakarta.inject.Inject;" )
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .contains( "private GenderJakartaSetterMapper genderJakartaSetterMapper;" )
            .doesNotContain( "@Inject" + lineSeparator() + "    private GenderJakartaSetterMapper" )
            .contains( method );
    }
}
