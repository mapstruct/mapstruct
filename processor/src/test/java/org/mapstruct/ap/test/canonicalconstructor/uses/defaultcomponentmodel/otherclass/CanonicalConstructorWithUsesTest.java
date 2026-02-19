/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.canonicalconstructor.uses.defaultcomponentmodel.otherclass;

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
    UserCanonicalConstructorWithUsesMapper.class,
    ContactRepository.class,
    AddressEntity.class,
    AddressDto.class,
    AddressMapper.class,
    ExampleMapper.class
})
@IssueKey("2257")
public class CanonicalConstructorWithUsesTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource();

    @ProcessorTest
    public void shouldConvertToTarget() throws Exception {
        // given
        AddressEntity addressEntity = new AddressEntity( "Nowogrodzka", 84, 86 );
        UserEntity userEntity = new UserEntity( 23, "Jan Kowalski", addressEntity );

        // when
        UserDto userDto = UserCanonicalConstructorWithUsesMapper.INSTANCE.map( userEntity );

        // then
        assertThat( userDto ).isNotNull();
        assertThat( userDto.getUserId() ).isEqualTo( 404 );
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
        generatedSource.forMapper( UserCanonicalConstructorWithUsesMapper.class )
            .content()
            .contains(
                "    public UserCanonicalConstructorWithUsesMapperImpl(ContactRepository contactRepository, AddressMapper addressMapper, ExampleMapper exampleMapper) {" +
                    lineSeparator() +
                    "        super( contactRepository, addressMapper );" + lineSeparator() +
                    "        this.exampleMapper = exampleMapper;" + lineSeparator() +
                    "    }" );
    }

    @ProcessorTest
    public void shouldHaveNoArgsConstructor() {
        generatedSource.forMapper( UserCanonicalConstructorWithUsesMapper.class )
            .content()
            .contains(
                "    public UserCanonicalConstructorWithUsesMapperImpl() {" + lineSeparator() +
                    "        super( new ContactRepository(), Mappers.getMapper( AddressMapper.class ) );" +
                    lineSeparator() +
                    "        this.exampleMapper = new ExampleMapper();" + lineSeparator() +
                    "    }" );
    }

}
