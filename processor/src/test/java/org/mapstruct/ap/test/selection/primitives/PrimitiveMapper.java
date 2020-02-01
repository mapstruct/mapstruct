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
public class PrimitiveMapper  {

    private static boolean calledUpon;

    public MyLong fromPrimitiveInt(int size)  {
        calledUpon = true;
        return new MyLong( (long) size );
    }

    public static boolean isCalledUpon() {
        return calledUpon;
    }

    public static void setCalledUpon( boolean calledUpon ) {
        PrimitiveMapper.calledUpon = calledUpon;
    }

}
