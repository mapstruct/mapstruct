/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.defaultcomponentmodel;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.AddressEntity;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserDto;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static java.lang.System.lineSeparator;
import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    UserEntity.class,
    UserDto.class,
    UserCanonicalConstructorMapper.class,
    ContactRepository.class,
    AddressEntity.class,
    AddressDto.class,
    AddressMapper.class,
})
@IssueKey("2257")
public class CanonicalConstructorTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldConvertToTarget() throws Exception {
        // given
        AddressEntity addressEntity = new AddressEntity( "Nowogrodzka", 84, 86 );
        UserEntity userEntity = new UserEntity( 23, "Jan Kowalski", addressEntity );

        // when
        UserDto userDto = UserCanonicalConstructorMapper.INSTANCE.map( userEntity );

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
        generatedSource.forMapper( UserCanonicalConstructorMapper.class )
            .content()
            .contains(
                "    public UserCanonicalConstructorMapperImpl(ContactRepository contactRepository, AddressMapper addressMapper) {" +
                    lineSeparator() +
                    "        super( contactRepository, addressMapper );" + lineSeparator() +
                    "    }" );
    }

    @ProcessorTest
    public void shouldHaveNoArgsConstructor() {
        generatedSource.forMapper( UserCanonicalConstructorMapper.class )
            .content()
            .contains(
                "    public UserCanonicalConstructorMapperImpl() {" + lineSeparator() +
                    "        super( new ContactRepository(), Mappers.getMapper( AddressMapper.class ) );" +
                    lineSeparator() +
                    "    }" );
    }

}
