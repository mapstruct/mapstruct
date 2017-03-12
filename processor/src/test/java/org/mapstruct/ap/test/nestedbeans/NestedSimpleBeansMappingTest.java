/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.test.nestedbeans;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.assertj.core.groups.Tuple;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

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
@RunWith(AnnotationProcessorTestRunner.class)
public class NestedSimpleBeansMappingTest {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        UserDtoMapperClassic.class,
        UserDtoMapperSmart.class,
        UserDtoUpdateMapperSmart.class
    );

    /**
     * Extracts all non synthetic declared fields from a class. This is needed, because jacoco adds a synthetic field
     * to the classes and also assertj does not support testing that all fields are exactly there.
     * This will be needed until <a href="https://github.com/joel-costigliola/assertj-core/issues/953">953</a> from
     * assertj-core is implemented.
     *
     * @param clazz to extract from
     *
     * @return all the non synthetic declared fields
     */
    private static List<Field> extractAllFields(Class<?> clazz) {
        List<Field> nonSyntheticFields = new ArrayList<Field>();
        for ( Field field : clazz.getDeclaredFields() ) {
            if ( !field.isSynthetic() ) {
                nonSyntheticFields.add( field );
            }
        }

        return nonSyntheticFields;
    }

    @Test
    public void shouldHaveAllFields() throws Exception {
        // If this test fails that means something new was added to the structure of the User/UserDto.
        // Make sure that the other tests are also updated (the new field is asserted)
        Object[] userFields = new String[] { "name", "car", "secondCar", "house" };
        assertThat( extractAllFields( User.class ) ).extracting( "name" ).containsExactlyInAnyOrder( userFields );
        assertThat( extractAllFields( UserDto.class ) ).extracting( "name" ).containsExactlyInAnyOrder( userFields );

        Object[] carFields = new String[] { "name", "year", "wheels" };
        assertThat( extractAllFields( Car.class ) ).extracting( "name" ).containsExactlyInAnyOrder( carFields );
        assertThat( extractAllFields( CarDto.class ) ).extracting( "name" ).containsExactlyInAnyOrder( carFields );

        Object[] wheelFields = new String[] { "front", "right" };
        assertThat( extractAllFields( Wheel.class ) ).extracting( "name" ).containsExactlyInAnyOrder( wheelFields );
        assertThat( extractAllFields( WheelDto.class ) ).extracting( "name" ).containsExactlyInAnyOrder( wheelFields );

        Object[] houseFields = new String[] { "name", "year", "roof" };
        assertThat( extractAllFields( House.class ) ).extracting( "name" ).containsExactlyInAnyOrder( houseFields );
        assertThat( extractAllFields( HouseDto.class ) ).extracting( "name" ).containsExactlyInAnyOrder( houseFields );

        Object[] roofFields = new String[] { "color", "type" };
        assertThat( extractAllFields( Roof.class ) ).extracting( "name" ).containsExactlyInAnyOrder( roofFields );
        assertThat( extractAllFields( RoofDto.class ) ).extracting( "name" ).containsExactlyInAnyOrder( roofFields );
    }

    @Test
    public void shouldMapNestedBeans() {

        User user = TestData.createUser();

        UserDto classicMapping = UserDtoMapperClassic.INSTANCE.userToUserDto( user );
        UserDto smartMapping = UserDtoMapperSmart.INSTANCE.userToUserDto( user );

        assertUserDto( classicMapping, user );
        assertUserDto( smartMapping, user );
    }

    @Test
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
        assertThat( smartMapping.getCar().getName() ).isEqualTo( "Toyota" );
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
        List<Tuple> wheelTuples = wheels.stream().map( new Function<Wheel, Tuple>() {
            @Override
            public Tuple apply(Wheel wheel) {
                return tuple( wheel.isFront(), wheel.isRight() );
            }
        } ).collect( Collectors.<Tuple>toList() );
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
