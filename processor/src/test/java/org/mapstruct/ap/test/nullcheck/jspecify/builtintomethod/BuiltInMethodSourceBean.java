/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintomethod;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import org.jspecify.annotations.Nullable;

public class BuiltInMethodSourceBean {

    @Nullable
    private JAXBElement<String> elem;

    @Nullable
    public JAXBElement<String> getElem() {
        return elem;
    }

    public void setElem(@Nullable JAXBElement<String> elem) {
        this.elem = elem;
    }

    public static JAXBElement<String> build(String value) {
        return new JAXBElement<>( new QName( "test" ), String.class, value );
    }
}
