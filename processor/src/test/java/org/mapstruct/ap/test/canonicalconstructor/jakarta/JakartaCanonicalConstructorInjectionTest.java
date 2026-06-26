/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.jakarta;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressEntity;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithJakartaInject;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;

@WithClasses({
    UserEntity.class,
    UserDto.class,
    UserJakartaCanonicalConstructorMapper.class,
    ContactRepository.class,
    AddressEntity.class,
    AddressDto.class,
    AddressMapper.class,
})
@IssueKey("2257")
@ComponentScan(basePackageClasses = JakartaCanonicalConstructorInjectionTest.class)
@Configuration
@WithJakartaInject
public class JakartaCanonicalConstructorInjectionTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @SuppressWarnings("LineLength")
    public void shouldHaveCanonicalConstructorInjection() {
        generatedSource.forMapper( UserJakartaCanonicalConstructorMapper.class )
            .content()
            .contains( "    @Inject" + lineSeparator() +
                "    public UserJakartaCanonicalConstructorMapperImpl(ContactRepository contactRepository, AddressMapper addressMapper) {" +
                lineSeparator() +
                "        super( contactRepository, addressMapper );" + lineSeparator() +
                "    }" );
    }

    @ProcessorTest
    public void shouldNotHaveNoArgsConstructor() {
        generatedSource.forMapper( UserJakartaCanonicalConstructorMapper.class )
            .content()
            .doesNotContain( "public UserJakartaCanonicalConstructorMapperImpl()" );
    }

    @ProcessorTest
    public void shouldContainProperImports() {
        generatedSource.forMapper( UserJakartaCanonicalConstructorMapper.class )
            .content()
            .contains( "import jakarta.inject.Inject;" )
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .doesNotContain( "javax.inject" );
    }
}
