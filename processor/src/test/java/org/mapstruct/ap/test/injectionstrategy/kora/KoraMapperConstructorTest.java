/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.injectionstrategy.kora;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.injectionstrategy.kora.constructor.ConstructorKoraConfig;
import org.mapstruct.ap.test.injectionstrategy.shared.*;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithTestDependency;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;

@WithClasses( {
        CustomerRecordDto.class,
        CustomerRecordEntity.class,
        CustomerDto.class,
        CustomerEntity.class,
        Gender.class,
        GenderDto.class,
        CustomerKoraConstructorMapper.class,
        GenderKoraConstructorMapper.class,
        ConstructorKoraConfig.class
} )
@WithTestDependency("common")
public class KoraMapperConstructorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldHaveConstructorInjection() {
        generatedSource.forMapper( CustomerKoraConstructorMapper.class )
            .content()
            .contains( "@Component" + lineSeparator() +
                "public class CustomerKoraConstructorMapperImpl" )
            .contains( "private final GenderKoraConstructorMapper" )
            .contains( "public CustomerKoraConstructorMapperImpl(GenderKoraConstructorMapper" );
    }

}
