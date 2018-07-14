/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

/**
 *
 * @author Sjaak Derksen
 */
public class ObjectFactory {

    public JAXBElement<String> createDate(String date) {
        return new JAXBElement<String>(  new QName( "dont-care" ), String.class, "06.07.2013" );
    }
}
