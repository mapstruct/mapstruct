/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2174;

import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2174")
@WithClasses(UserMapper.class)
public class Issue2174Test {

    @ProcessorTest
    public void shouldNotWrapCheckedException() throws Exception {

        UserMapper.User user = UserMapper.INSTANCE.map( new UserMapper.UserDto( "Test City", "10000" ) );

        assertThat( user ).isNotNull();
        assertThat( user.getAddress() ).isNotNull();
        assertThat( user.getAddress().getCity() ).isNotNull();
        assertThat( user.getAddress().getCity().getName() ).isEqualTo( "Test City" );
        assertThat( user.getAddress().getCode() ).isNotNull();
        assertThat( user.getAddress().getCode().getCode() ).isEqualTo( "10000" );

        assertThatThrownBy( () -> UserMapper.INSTANCE.map( new UserMapper.UserDto( "Zurich", "10000" ) ) )
            .isInstanceOf( UserMapper.CityNotFoundException.class )
            .hasMessage( "City with name 'Zurich' does not exist" );

        assertThatThrownBy( () -> UserMapper.INSTANCE.map( new UserMapper.UserDto( "Test City", "1000" ) ) )
            .isInstanceOf( UserMapper.PostalCodeNotFoundException.class )
            .hasMessage( "Postal code '1000' does not exist" );
    }
}
