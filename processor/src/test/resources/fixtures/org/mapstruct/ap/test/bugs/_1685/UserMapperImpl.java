/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1685;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-01-27T12:40:32+0100",
    comments = "version: , compiler: Eclipse JDT (Batch) 1.2.100.v20160418-1457, environment: Java 1.8.0_181 (Oracle Corporation)"
)
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO userToUserDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        userDTO.setContactDataDTO( userToContactDataDTO( user ) );
        userDTO.setName( user.getName() );

        return userDTO;
    }

    @Override
    public void updateUserFromUserDTO(UserDTO userDTO, User user) {
        if ( userDTO == null ) {
            return;
        }

        user.setEmail( userDTOContactDataDTOEmail( userDTO ) );
        String phone = userDTOContactDataDTOPhone( userDTO );
        if ( phone != null ) {
            user.setPhone( Integer.parseInt( phone ) );
        }
        else {
            user.setPhone( null );
        }
        user.setAddress( userDTOContactDataDTOAddress( userDTO ) );
        List<String> preferences = userDTOContactDataDTOPreferences( userDTO );
        if ( preferences != null ) {
            for ( String contactDataDTOPreference : preferences ) {
                user.addPreference( contactDataDTOPreference );
            }
        }
        String[] settings1 = userDTOContactDataDTOSettings( userDTO );
        if ( settings1 != null ) {
            user.setSettings( Arrays.copyOf( settings1, settings1.length ) );
        }
        else {
            user.setSettings( null );
        }
        user.setName( userDTO.getName() );
    }

    @Override
    public void updateUserFromUserAndIgnoreDTO(UserDTO userDTO, User user) {
        if ( userDTO == null ) {
            return;
        }

        String email = userDTOContactDataDTOEmail( userDTO );
        if ( email != null ) {
            user.setEmail( email );
        }
        String phone = userDTOContactDataDTOPhone( userDTO );
        if ( phone != null ) {
            user.setPhone( Integer.parseInt( phone ) );
        }
        String address = userDTOContactDataDTOAddress( userDTO );
        if ( address != null ) {
            user.setAddress( address );
        }
        List<String> preferences = userDTOContactDataDTOPreferences( userDTO );
        if ( preferences != null ) {
            for ( String contactDataDTOPreference : preferences ) {
                user.addPreference( contactDataDTOPreference );
            }
        }
        String[] settings1 = userDTOContactDataDTOSettings( userDTO );
        if ( settings1 != null ) {
            user.setSettings( Arrays.copyOf( settings1, settings1.length ) );
        }
        if ( userDTO.getName() != null ) {
            user.setName( userDTO.getName() );
        }
    }

    @Override
    public void updateUserFromUserAndDefaultDTO(UserDTO userDTO, User user) {
        if ( userDTO == null ) {
            return;
        }

        String email = userDTOContactDataDTOEmail( userDTO );
        if ( email != null ) {
            user.setEmail( email );
        }
        else {
            user.setEmail( "" );
        }
        String phone = userDTOContactDataDTOPhone( userDTO );
        if ( phone != null ) {
            user.setPhone( Integer.parseInt( phone ) );
        }
        else {
            user.setPhone( 0 );
        }
        String address = userDTOContactDataDTOAddress( userDTO );
        if ( address != null ) {
            user.setAddress( address );
        }
        else {
            user.setAddress( "" );
        }
        List<String> preferences = userDTOContactDataDTOPreferences( userDTO );
        if ( preferences != null ) {
            for ( String contactDataDTOPreference : preferences ) {
                user.addPreference( contactDataDTOPreference );
            }
        }
        String[] settings1 = userDTOContactDataDTOSettings( userDTO );
        if ( settings1 != null ) {
            user.setSettings( Arrays.copyOf( settings1, settings1.length ) );
        }
        else {
            user.setSettings( new String[0] );
        }
        if ( userDTO.getName() != null ) {
            user.setName( userDTO.getName() );
        }
        else {
            user.setName( "" );
        }
    }

    protected ContactDataDTO userToContactDataDTO(User user) {
        if ( user == null ) {
            return null;
        }

        ContactDataDTO contactDataDTO = new ContactDataDTO();

        contactDataDTO.setEmail( user.getEmail() );
        if ( user.getPhone() != null ) {
            contactDataDTO.setPhone( String.valueOf( user.getPhone() ) );
        }
        contactDataDTO.setAddress( user.getAddress() );
        List<String> list = user.getPreferences();
        if ( list != null ) {
            contactDataDTO.setPreferences( new ArrayList<String>( list ) );
        }
        String[] settings = user.getSettings();
        if ( settings != null ) {
            contactDataDTO.setSettings( Arrays.copyOf( settings, settings.length ) );
        }

        return contactDataDTO;
    }

    private String userDTOContactDataDTOEmail(UserDTO userDTO) {
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        return contactDataDTO.getEmail();
    }

    private String userDTOContactDataDTOPhone(UserDTO userDTO) {
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        return contactDataDTO.getPhone();
    }

    private String userDTOContactDataDTOAddress(UserDTO userDTO) {
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        return contactDataDTO.getAddress();
    }

    private List<String> userDTOContactDataDTOPreferences(UserDTO userDTO) {
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        return contactDataDTO.getPreferences();
    }

    private String[] userDTOContactDataDTOSettings(UserDTO userDTO) {
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        return contactDataDTO.getSettings();
    }
}
