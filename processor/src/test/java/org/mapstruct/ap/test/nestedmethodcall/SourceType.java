/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import javax.xml.bind.JAXBElement;

/**
 * @author Sjaak Derksen
 */
public class SourceType {

    private JAXBElement<String> date;

    public JAXBElement<String> getDate() {
        return date;
    }

    public void setDate( JAXBElement<String> date ) {
        this.date = date;
    }
}
