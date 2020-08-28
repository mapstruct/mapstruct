/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2174;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * @author Filip Hrisafov
 */
@IssueKey("2174")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(UserMapper.class)
public class Issue2174Test {

    @Test
    public void shouldNotWrapCheckedException() throws Exception {

        UserMapper.User user = UserMapper.INSTANCE.map( new UserMapper.UserDto( "Test City" ) );

        assertThat( user ).isNotNull();
        assertThat( user.getAddress() ).isNotNull();
        assertThat( user.getAddress().getCity() ).isNotNull();
        assertThat( user.getAddress().getCity().getName() ).isEqualTo( "Test City" );

        assertThatThrownBy( () -> UserMapper.INSTANCE.map( new UserMapper.UserDto( "Zurich" ) ) )
            .isInstanceOf( UserMapper.CityNotFoundException.class )
            .hasMessage( "City with name 'Zurich' does not exist" );
    }
}
