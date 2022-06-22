/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1685;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.extension.RegisterExtension;
import org.mapstruct.ap.testutil.runner.GeneratedSource;
import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@IssueKey("1685")
@WithClasses({
    User.class,
    UserDTO.class,
    UserMapper.class,
    ContactDataDTO.class
})
public class Issue1685Test {

    @RegisterExtension
    final GeneratedSource generatedSource = new GeneratedSource().addComparisonToFixtureFor(
        UserMapper.class
    );

    @ProcessorTest
    public void testSetToNullWhenNVPMSSetToNull() {

        User target = new User();
        target.setAddress( "address" );
        target.setEmail( "email" );
        target.setName( "name" );
        target.setPhone( 12345 );
        target.addPreference( "preference" );
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
        assertThat( target.getPreferences() ).containsOnly( "preference" );
        assertThat( target.getSettings() ).isNull();
    }

    @ProcessorTest
    public void testIgnoreWhenNVPMSIgnore() {

        User target = new User();
        target.setAddress( "address" );
        target.setEmail( "email" );
        target.setName( "name" );
        target.setPhone( 12345 );
        target.addPreference( "preference" );
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
        assertThat( target.getPreferences() ).containsOnly( "preference" );
        assertThat( target.getSettings() ).containsExactly( "test" );
    }

    @ProcessorTest
    public void testSetToDefaultWhenNVPMSSetToDefault() {

        User target = new User();
        target.setAddress( "address" );
        target.setEmail( "email" );
        target.setName( "name" );
        target.setPhone( 12345 );
        target.addPreference( "preference" );
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
        assertThat( target.getPreferences() ).containsOnly( "preference" );
        assertThat( target.getSettings() ).isEmpty();
    }

}
