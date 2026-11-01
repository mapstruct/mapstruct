/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintobuiltIn;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.jspecify.annotations.Nullable;

public class BuiltInToBuiltInSourceBean {

    @Nullable
    private JAXBElement<Calendar> elem;

    @Nullable
    public JAXBElement<Calendar> getElem() {
        return elem;
    }

    public void setElem(@Nullable JAXBElement<Calendar> elem) {
        this.elem = elem;
    }

    public static JAXBElement<Calendar> build(Calendar cal) {
        return new JAXBElement<>( new QName( "test" ), Calendar.class, cal );
    }

    public static JAXBElement<Calendar> buildDefault() {
        return build( new GregorianCalendar( 2020, Calendar.JANUARY, 15 ) );
    }
}
