/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.uses.jakarta;

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
    UserJakartaCanonicalConstructorWithUsesMapper.class,
    ContactRepository.class,
    AddressEntity.class,
    AddressDto.class,
    AddressMapper.class,
    ExampleMapper.class
})
@IssueKey("2257")
@ComponentScan(basePackageClasses = JakartaCanonicalConstructorWithUsesInjectionTest.class)
@Configuration
@WithJakartaInject
public class JakartaCanonicalConstructorWithUsesInjectionTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    @SuppressWarnings("LineLength")
    public void shouldHaveCanonicalConstructorInjection() {
        generatedSource.forMapper( UserJakartaCanonicalConstructorWithUsesMapper.class )
            .content()
            .contains( "    @Inject" + lineSeparator() +
                "    public UserJakartaCanonicalConstructorWithUsesMapperImpl(ContactRepository contactRepository, AddressMapper addressMapper, ExampleMapper exampleMapper) {" +
                lineSeparator() +
                "        super( contactRepository, addressMapper );" + lineSeparator() +
                "        this.exampleMapper = exampleMapper;" + lineSeparator() +
                "    }" );
    }

    @ProcessorTest
    public void shouldNotHaveNoArgsConstructor() {
        generatedSource.forMapper( UserJakartaCanonicalConstructorWithUsesMapper.class )
            .content()
            .doesNotContain( "public UserJakartaCanonicalConstructorWithUsesMapperImpl()" );
    }

    @ProcessorTest
    public void shouldContainProperImports() {
        generatedSource.forMapper( UserJakartaCanonicalConstructorWithUsesMapper.class )
            .content()
            .contains( "import jakarta.inject.Inject;" )
            .contains( "import jakarta.inject.Named;" )
            .contains( "import jakarta.inject.Singleton;" )
            .doesNotContain( "javax.inject" );
    }
}
