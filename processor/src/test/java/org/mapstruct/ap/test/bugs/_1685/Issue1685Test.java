/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1685;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.ap.testutil.runner.GeneratedSource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("1685")
@WithClasses({
    User.class,
    UserDTO.class,
    UserMapper.class,
    ContactDataDTO.class
})
public class Issue1685Test {

    @Rule
    public final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        UserMapper.class
    );

    @Test
    public void testSetToNullWhenNVPMSSetToNull() {

        User target = new User();
        target.setAddress( "address" );
        target.setEmail( "email" );
        target.setName( "name" );
        target.setPhone( 12345 );
        target.setSettings( new String[]{ "test" } );

        UserDTO source = new UserDTO();
        source.setContactDataDTO( new ContactDataDTO() );
        source.getContactDataDTO().setAddress( "newAddress" );
        source.getContactDataDTO().setEmail( null );
        source.getContactDataDTO().setPhone( null );
        source.setName( null );

        UserMapper.INSTANCE.updateUserFromUserDTO( source, target );

        assertThat( target.getAddress() ).isEqualTo( "newAddress" );
        assertThat( target.getEmail() ).isNull();
        assertThat( target.getPhone() ).isNull();
        assertThat( target.getName() ).isNull();
        assertThat( target.getPreferences() ).isEmpty();
        assertThat( target.getSettings() ).isNull();
    }

    @Test
    public void testIgnoreWhenNVPMSIgnore() {

        User target = new User();
        target.setAddress( "address" );
        target.setEmail( "email" );
        target.setName( "name" );
        target.setPhone( 12345 );
        target.setSettings( new String[]{ "test" } );

        UserDTO source = new UserDTO();
        source.setContactDataDTO( new ContactDataDTO() );
        source.getContactDataDTO().setAddress( "newAddress" );
        source.getContactDataDTO().setEmail( null );
        source.getContactDataDTO().setPhone( null );
        source.setName( null );

        UserMapper.INSTANCE.updateUserFromUserAndIgnoreDTO( source, target );

        assertThat( target.getAddress() ).isEqualTo( "newAddress" );
        assertThat( target.getEmail() ).isEqualTo( "email" );
        assertThat( target.getPhone() ).isEqualTo( 12345 );
        assertThat( target.getName() ).isEqualTo( "name" );
        assertThat( target.getPreferences() ).isEmpty();
        assertThat( target.getSettings() ).containsExactly( "test" );
    }

    @Test
    public void testSetToDefaultWhenNVPMSSetToDefault() {

        User target = new User();
        target.setAddress( "address" );
        target.setEmail( "email" );
        target.setName( "name" );
        target.setPhone( 12345 );
        target.setSettings( new String[]{ "test" } );

        UserDTO source = new UserDTO();
        source.setContactDataDTO( new ContactDataDTO() );
        source.getContactDataDTO().setAddress( "newAddress" );
        source.getContactDataDTO().setEmail( null );
        source.getContactDataDTO().setPhone( null );
        source.setName( null );

        UserMapper.INSTANCE.updateUserFromUserAndDefaultDTO( source, target );

        assertThat( target.getAddress() ).isEqualTo( "newAddress" );
        assertThat( target.getEmail() ).isEqualTo( "" );
        assertThat( target.getPhone() ).isEqualTo( 0 );
        assertThat( target.getName() ).isEqualTo( "" );
        assertThat( target.getPreferences() ).isEmpty();
        assertThat( target.getSettings() ).isEmpty();
    }

}
