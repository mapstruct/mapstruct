/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3896;

/**
 * Enum whose codes deliberately differ from the ordinal values.
 */
public enum UserState {

    ENABLED( 1 ),
    DISABLED( 2 );

    private final int code;

    UserState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static UserState fromCode(int code) {
        for ( UserState value : values() ) {
            if ( value.code == code ) {
                return value;
            }
        }
        throw new IllegalArgumentException( "Unknown code: " + code );
    }
}
