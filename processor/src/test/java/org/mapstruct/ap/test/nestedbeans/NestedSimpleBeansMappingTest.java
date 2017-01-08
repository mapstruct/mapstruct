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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@WithClasses({
    User.class, UserDto.class, Car.class, CarDto.class, House.class, HouseDto.class,
    Wheel.class, WheelDto.class,
    Roof.class, RoofDto.class,
    UserDtoMapperClassic.class,
    UserDtoMapperSmart.class,
    UserDtoUpdateMapperSmart.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class NestedSimpleBeansMappingTest {

    @Test
    public void shouldMapNestedBeans() {

        User user = TestData.createUser();

        UserDto classicMapping = UserDtoMapperClassic.INSTANCE.userToUserDto( user );
        UserDto smartMapping = UserDtoMapperSmart.INSTANCE.userToUserDto( user );

        System.out.println( smartMapping );
        System.out.println( classicMapping );

        assertThat( smartMapping ).isNotNull();
        assertThat( smartMapping ).isEqualTo( classicMapping );
    }

    @Test
    public void shouldMapUpdateNestedBeans() {

        User user = TestData.createUser();
        user.getCar().setName( null );

        // create a pre-exsiting smartMapping
        UserDto smartMapping = new UserDto();
        smartMapping.setCar( new CarDto() );
        smartMapping.getCar().setName( "Toyota" );

        // create a classic mapping and adapt expected result to Toyota
        UserDto classicMapping = UserDtoMapperClassic.INSTANCE.userToUserDto( TestData.createUser() );
        classicMapping.getCar().setName( "Toyota" );

        // action
        UserDtoUpdateMapperSmart.INSTANCE.userToUserDto( smartMapping, user );

        // result
        assertThat( smartMapping ).isNotNull();
        assertThat( smartMapping ).isEqualTo( classicMapping );
    }
}
