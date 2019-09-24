/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.presencecheck;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

/**
 * Test for using an inverted presence checker strategy.
 *
 * @author Kirill Baurchanu
 */
public class InvertedPresenceCheckTest {

    @Test
    public void shouldApplyInvertedPresenceCheckerStrategy() {
        User user = new User();
        user.setName( "John Doe" );
        user.setContacts( new User.ContactInformation() );
        user.getContacts().setAddress( "" );
        user.getContacts().setEmail( "" );
        user.setRoles( Collections.emptyList() );

        UserDTO dto = UserMapper.INSTANCE.map( user );
        assertThat( dto.getName() ).isEqualTo( "John Doe" );
        assertThat( dto.getAddress() ).isNull();
        assertThat( dto.getEmail() ).isNull();
        assertThat( dto.getRoles() ).isNull();
    }

}
