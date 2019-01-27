/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1685;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-01-27T10:32:39+0100",
    comments = "version: , compiler: javac, environment: Java 1.8.0_181 (Oracle Corporation)"
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

        if ( userDTOContactDataDTOPreferences( userDTO ) != null ) {
            for ( String contactDataDTOPreference : userDTOContactDataDTOPreferences( userDTO ) ) {
                user.addPreference( contactDataDTOPreference );
            }
        }
        user.setAddress( userDTOContactDataDTOAddress( userDTO ) );
        String phone = userDTOContactDataDTOPhone( userDTO );
        if ( phone != null ) {
            user.setPhone( Integer.parseInt( phone ) );
        }
        else {
            user.setPhone( null );
        }
        user.setEmail( userDTOContactDataDTOEmail( userDTO ) );
        user.setName( userDTO.getName() );
    }

    @Override
    public void updateUserFromUserAndIgnoreDTO(UserDTO userDTO, User user) {
        if ( userDTO == null ) {
            return;
        }

        if ( userDTOContactDataDTOPreferences( userDTO ) != null ) {
            for ( String contactDataDTOPreference : userDTOContactDataDTOPreferences( userDTO ) ) {
                user.addPreference( contactDataDTOPreference );
            }
        }
        String address = userDTOContactDataDTOAddress( userDTO );
        if ( address != null ) {
            user.setAddress( address );
        }
        String phone = userDTOContactDataDTOPhone( userDTO );
        if ( phone != null ) {
            user.setPhone( Integer.parseInt( phone ) );
        }
        String email = userDTOContactDataDTOEmail( userDTO );
        if ( email != null ) {
            user.setEmail( email );
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

        if ( userDTOContactDataDTOPreferences( userDTO ) != null ) {
            for ( String contactDataDTOPreference : userDTOContactDataDTOPreferences( userDTO ) ) {
                user.addPreference( contactDataDTOPreference );
            }
        }
        String address = userDTOContactDataDTOAddress( userDTO );
        if ( address != null ) {
            user.setAddress( address );
        }
        else {
            user.setAddress( new String() );
        }
        String phone = userDTOContactDataDTOPhone( userDTO );
        if ( phone != null ) {
            user.setPhone( Integer.parseInt( phone ) );
        }
        else {
            user.setPhone( 0 );
        }
        String email = userDTOContactDataDTOEmail( userDTO );
        if ( email != null ) {
            user.setEmail( email );
        }
        else {
            user.setEmail( new String() );
        }
        if ( userDTO.getName() != null ) {
            user.setName( userDTO.getName() );
        }
        else {
            user.setName( new String() );
        }
    }

    protected ContactDataDTO userToContactDataDTO(User user) {
        if ( user == null ) {
            return null;
        }

        ContactDataDTO contactDataDTO = new ContactDataDTO();

        if ( user.getPhone() != null ) {
            contactDataDTO.setPhone( String.valueOf( user.getPhone() ) );
        }
        List<String> list = user.getPreferences();
        if ( list != null ) {
            contactDataDTO.setPreferences( new ArrayList<String>( list ) );
        }
        contactDataDTO.setAddress( user.getAddress() );
        contactDataDTO.setEmail( user.getEmail() );

        return contactDataDTO;
    }

    private List<String> userDTOContactDataDTOPreferences(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        List<String> preferences = contactDataDTO.getPreferences();
        if ( preferences == null ) {
            return null;
        }
        return preferences;
    }

    private String userDTOContactDataDTOAddress(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        String address = contactDataDTO.getAddress();
        if ( address == null ) {
            return null;
        }
        return address;
    }

    private String userDTOContactDataDTOPhone(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        String phone = contactDataDTO.getPhone();
        if ( phone == null ) {
            return null;
        }
        return phone;
    }

    private String userDTOContactDataDTOEmail(UserDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }
        ContactDataDTO contactDataDTO = userDTO.getContactDataDTO();
        if ( contactDataDTO == null ) {
            return null;
        }
        String email = contactDataDTO.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
