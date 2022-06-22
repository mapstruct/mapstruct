/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.jsr330.jakarta;

import static java.lang.System.lineSeparator;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithJakartaInject;
import org.mapstruct.ap.testutil.WithJavaxInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Test field injection for component model jsr330.
 * Default value option mapstruct.defaultInjectionStrategy is "field"
 *
 * @author Filip Hrisafov
 */
@IssueKey("2567")
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerJsr330DefaultCompileOptionFieldMapper.class,
    GenderJsr330DefaultCompileOptionFieldMapper.class
})
@WithJakartaInject
@WithJavaxInject
public class JakartaAndJsr330DefaultCompileOptionFieldMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveJavaxInjection() {
        generatedSource.forMapper( CustomerJsr330DefaultCompileOptionFieldMapper.class )
            .content()
            .contains( "import javax.inject.Inject;" )
            .contains( "import javax.inject.Named;" )
            .contains( "import javax.inject.Singleton;" )
            .contains( "@Inject" + lineSeparator() + "    private GenderJsr330DefaultCompileOptionFieldMapper" )
            .doesNotContain( "public CustomerJsr330DefaultCompileOptionFieldMapperImpl(" )
            .doesNotContain( "jakarta.inject" );
    }
}
