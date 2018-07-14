/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.itest.cdi;

import java.util.Date;
import java.util.GregorianCalendar;

public class Source {

    private int foo = 42;

    private Date date = new GregorianCalendar( 1980, 0, 1 ).getTime();

    public int getFoo() {
        return foo;
    }

    public Date getDate() {
        return date;
    }
}
