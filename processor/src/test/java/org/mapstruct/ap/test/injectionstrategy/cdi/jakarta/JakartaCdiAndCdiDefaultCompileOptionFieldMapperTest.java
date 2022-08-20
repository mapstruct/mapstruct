/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.cdi.jakarta;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerDto;
import org.mapstruct.ap.test.injectionstrategy.shared.CustomerEntity;
import org.mapstruct.ap.test.injectionstrategy.shared.Gender;
import org.mapstruct.ap.test.injectionstrategy.shared.GenderDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithCdi;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJakartaCdi;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;

/**
 * Test field injection for component model jsr330.
 * Default value option mapstruct.defaultInjectionStrategy is "field"
 *
 * @author Filip Hrisafov
 */
@IssueKey("2950")
@WithClasses({
    CustomerDto.class,
    CustomerEntity.class,
    Gender.class,
    GenderDto.class,
    CustomerCdiDefaultCompileOptionFieldMapper.class,
    GenderCdiDefaultCompileOptionFieldMapper.class
})
@WithJakartaCdi
@WithCdi
public class JakartaCdiAndCdiDefaultCompileOptionFieldMapperTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveCdiInjection() {
        generatedSource.forMapper( CustomerCdiDefaultCompileOptionFieldMapper.class )
            .content()
            .contains( "import javax.enterprise.context.ApplicationScoped;" )
            .contains( "import javax.inject.Inject;" )
            .contains( "@Inject" + lineSeparator() + "    private GenderCdiDefaultCompileOptionFieldMapper" )
            .contains( "@ApplicationScoped" + lineSeparator() + "public class" )
            .doesNotContain( "public CustomerCdiDefaultCompileOptionFieldMapperImpl(" )
            .doesNotContain( "jakarta.inject" )
            .doesNotContain( "jakarta.enterprise" );
    }
}
