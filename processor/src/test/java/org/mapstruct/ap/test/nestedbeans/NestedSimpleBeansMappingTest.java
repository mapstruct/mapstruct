/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedbeans;

import java.util.List;
import java.util.stream.Collectors;

import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;
import org.mapstruct.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

@WithClasses({
    User.class, UserDto.class, Car.class, CarDto.class, House.class, HouseDto.class,
    Wheel.class, WheelDto.class,
    Roof.class, RoofDto.class,
    RoofType.class, ExternalRoofType.class,
    org.mapstruct.ap.test.nestedbeans.other.CarDto.class,
    org.mapstruct.ap.test.nestedbeans.other.UserDto.class,
    org.mapstruct.ap.test.nestedbeans.other.HouseDto.class,
    org.mapstruct.ap.test.nestedbeans.other.RoofDto.class,
    org.mapstruct.ap.test.nestedbeans.other.WheelDto.class,
    UserDtoMapperClassic.class,
    UserDtoMapperSmart.class,
    UserDtoUpdateMapperSmart.class
})
public class NestedSimpleBeansMappingTest {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        UserDtoMapperClassic.class,
        UserDtoMapperSmart.class,
        UserDtoUpdateMapperSmart.class
    );

    @ProcessorTest
    public void shouldHaveAllFields() {
        // If this test fails that means something new was added to the structure of the User/UserDto.
        // Make sure that the other tests are also updated (the new field is asserted)
        String[] userFields = new String[] { "name", "car", "secondCar", "house" };
        assertThat( User.class ).hasOnlyDeclaredFields( userFields );
        assertThat( UserDto.class ).hasOnlyDeclaredFields( userFields );

        String[] carFields = new String[] { "name", "year", "wheels" };
        assertThat( Car.class ).hasOnlyDeclaredFields( carFields );
        assertThat( CarDto.class ).hasOnlyDeclaredFields( carFields );

        String[] wheelFields = new String[] { "front", "right" };
        assertThat( Wheel.class ).hasOnlyDeclaredFields( wheelFields );
        assertThat( WheelDto.class ).hasOnlyDeclaredFields( wheelFields );

        String[] houseFields = new String[] { "name", "year", "roof" };
        assertThat( House.class ).hasOnlyDeclaredFields( houseFields );
        assertThat( HouseDto.class ).hasOnlyDeclaredFields( houseFields );

        String[] roofFields = new String[] { "color", "type" };
        assertThat( Roof.class ).hasOnlyDeclaredFields( roofFields );
        assertThat( RoofDto.class ).hasOnlyDeclaredFields( roofFields );
    }

    @ProcessorTest
    public void shouldMapNestedBeans() {

        User user = TestData.createUser();

        UserDto classicMapping = UserDtoMapperClassic.INSTANCE.userToUserDto( user );
        UserDto smartMapping = UserDtoMapperSmart.INSTANCE.userToUserDto( user );

        assertUserDto( classicMapping, user );
        assertUserDto( smartMapping, user );
    }

    @ProcessorTest
    public void shouldMapUpdateNestedBeans() {

        User user = TestData.createUser();
        user.getCar().setName( null );

        // create a pre-exsiting smartMapping
        UserDto smartMapping = new UserDto();
        smartMapping.setCar( new CarDto() );
        smartMapping.getCar().setName( "Toyota" );

        // action
        UserDtoUpdateMapperSmart.INSTANCE.userToUserDto( smartMapping, user );

        // result
        assertThat( smartMapping.getName() ).isEqualTo( user.getName() );
        assertThat( smartMapping.getCar().getYear() ).isEqualTo( user.getCar().getYear() );
        assertThat( smartMapping.getCar().getName() ).isNull();
        assertThat( user.getCar().getName() ).isNull();
        assertWheels( smartMapping.getCar().getWheels(), user.getCar().getWheels() );
        assertCar( smartMapping.getSecondCar(), user.getSecondCar() );
        assertHouse( smartMapping.getHouse(), user.getHouse() );
    }

    private static void assertUserDto(UserDto userDto, User user) {
        assertThat( userDto ).isNotNull();
        assertThat( userDto.getName() ).isEqualTo( user.getName() );
        assertCar( userDto.getCar(), user.getCar() );
        assertCar( userDto.getSecondCar(), user.getSecondCar() );
        assertHouse( userDto.getHouse(), user.getHouse() );
    }

    private static void assertCar(CarDto carDto, Car car) {
        if ( car == null ) {
            assertThat( carDto ).isNull();
        }
        else {
            assertThat( carDto.getName() ).isEqualTo( car.getName() );
            assertThat( carDto.getYear() ).isEqualTo( car.getYear() );
            assertWheels( carDto.getWheels(), car.getWheels() );
        }
    }

    private static void assertWheels(List<WheelDto> wheelDtos, List<Wheel> wheels) {
        List<Tuple> wheelTuples = wheels.stream()
            .map( wheel -> tuple( wheel.isFront(), wheel.isRight() ) )
            .collect( Collectors.toList() );
        assertThat( wheelDtos )
            .extracting( "front", "right" )
            .containsExactlyElementsOf( wheelTuples );
    }

    private static void assertHouse(HouseDto houseDto, House house) {
        assertThat( houseDto.getName() ).isEqualTo( house.getName() );
        assertThat( houseDto.getYear() ).isEqualTo( house.getYear() );
        assertRoof( houseDto.getRoof(), house.getRoof() );
    }

    private static void assertRoof(RoofDto roofDto, Roof roof) {
        assertThat( roofDto.getColor() ).isEqualTo( String.valueOf( roof.getColor() ) );
        assertThat( roofDto.getType().name() ).isEqualTo( roof.getType().name() );
    }
}
