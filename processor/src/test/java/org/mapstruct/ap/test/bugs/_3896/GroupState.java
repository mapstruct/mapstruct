/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3896;

/**
 * Enum whose codes deliberately differ from the ordinal values, so that a custom {@code int -> enum} mapping
 * method can be distinguished from the built-in ordinal based conversion.
 */
public enum GroupState {

    ACTIVE( 10 ),
    INACTIVE( 20 );

    private final int code;

    GroupState(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static GroupState fromCode(int code) {
        for ( GroupState value : values() ) {
            if ( value.code == code ) {
                return value;
            }
        }
        throw new IllegalArgumentException( "Unknown code: " + code );
    }
}
