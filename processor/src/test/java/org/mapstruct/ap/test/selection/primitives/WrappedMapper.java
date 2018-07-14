/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.primitives;

/**
 * @author Sjaak Derksen
 *
 */
public class WrappedMapper {

    private static boolean calledUpon;

    public MyLong fromWrappedInt( Integer size ) {
        calledUpon = true;
        return new MyLong( size != null ? (long) size : null );
    }

    public static boolean isCalledUpon() {
        return calledUpon;
    }

    public static void setCalledUpon( boolean calledUpon ) {
        WrappedMapper.calledUpon = calledUpon;
    }

}
