/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330.setter;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJavaxInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerJsr330SetterMapper.class,
    GenderJsr330SetterMapper.class,
    SetterJsr330Config.class
})
@IssueKey("3229")
@WithJavaxInject
public class Jsr330SetterMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveSetterInjection() {
        String method =  "@Inject" + lineSeparator() +
            "    public void setGenderJsr330SetterMapper(GenderJsr330SetterMapper genderJsr330SetterMapper) {" +
            lineSeparator() + "        this.genderJsr330SetterMapper = genderJsr330SetterMapper;" +
            lineSeparator() + "    }";
        generatedSource.forMapper( CustomerJsr330SetterMapper.class )
            .content()
            .contains( "import javax.inject.Inject;" )
            .contains( "import javax.inject.Named;" )
            .contains( "import javax.inject.Singleton;" )
            .contains( "private GenderJsr330SetterMapper genderJsr330SetterMapper;" )
            .doesNotContain( "@Inject" + lineSeparator() + "    private GenderJsr330SetterMapper" )
            .contains( method );
    }
}
