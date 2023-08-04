/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2663;

/**
 * @author Filip Hrisafov
 */
public class NullableHelper {

    private NullableHelper() {
        // Helper class
    }

    public static <T> Nullable<T> jsonNullableToNullable(JsonNullable<T> jsonNullable) {
        if ( jsonNullable.isPresent() ) {
            return Nullable.of( jsonNullable.get() );
        }
        return Nullable.undefined();
    }
}
