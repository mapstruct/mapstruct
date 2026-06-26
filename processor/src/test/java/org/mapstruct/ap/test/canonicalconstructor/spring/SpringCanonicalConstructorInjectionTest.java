/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.spring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressEntity;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.WithSpring;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    UserEntity.class,
    UserDto.class,
    UserSpringCanonicalConstructorMapper.class,
    ContactRepository.class,
    AddressEntity.class,
    AddressDto.class,
    AddressMapper.class,
})
@IssueKey("2257")
@ComponentScan(basePackageClasses = SpringCanonicalConstructorInjectionTest.class)
@Configuration
@WithSpring
public class SpringCanonicalConstructorInjectionTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @Autowired
    private UserSpringCanonicalConstructorMapper userMapper;
    private ConfigurableApplicationContext context;

    @BeforeEach
    public void springUp() {
        context = new AnnotationConfigApplicationContext( getClass() );
        context.getAutowireCapableBeanFactory().autowireBean( this );
    }

    @AfterEach
    public void springDown() {
        if ( context != null ) {
            context.close();
        }
    }

    @ProcessorTest
    public void shouldConvertToTarget() throws Exception {
        // given
        AddressEntity addressEntity = new AddressEntity( "Nowogrodzka", 84, 86 );
        UserEntity userEntity = new UserEntity( 23, "Jan Kowalski", addressEntity );

        // when
        UserDto userDto = userMapper.map( userEntity );

        // then
        assertThat( userDto ).isNotNull();
        assertThat( userDto.getUserId() ).isEqualTo( 23 );
        assertThat( userDto.getName() ).isEqualTo( "Jan Kowalski" );
        assertThat( userDto.getPhoneNumber() ).isEqualTo( "+441134960000" );
        AddressDto addressDto = userDto.getAddress();
        assertThat( addressDto.getStreet() ).isEqualTo( "Nowogrodzka" );
        assertThat( addressDto.getBuilding() ).isEqualTo( 84 );
        assertThat( addressDto.getFlat() ).isEqualTo( 86 );
    }

    @ProcessorTest
    @SuppressWarnings("LineLength")
    public void shouldHaveCanonicalConstructorInjection() {
        generatedSource.forMapper( UserSpringCanonicalConstructorMapper.class )
            .content()
            .contains(
                "    public UserSpringCanonicalConstructorMapperImpl(ContactRepository contactRepository, AddressMapper addressMapper) {" +
                    lineSeparator() +
                    "        super( contactRepository, addressMapper );" + lineSeparator() +
                    "    }" );
    }

    @ProcessorTest
    public void shouldNotHaveNoArgsConstructor() {
        generatedSource.forMapper( UserSpringCanonicalConstructorMapper.class )
            .content()
            .doesNotContain( "public UserSpringCanonicalConstructorMapperImpl()" );
    }

}
