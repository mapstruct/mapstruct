/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nestedmethodcall;

import java.util.List;

import javax.xml.bind.JAXBElement;

/**
 * @author Sjaak Derksen
 */
public class OrderDetailsType {

    private JAXBElement<String> name;
    private List<JAXBElement<String>> description;

    public JAXBElement<String> getName() {
        return name;
    }

    public void setName(JAXBElement<String> value) {
        this.name = value;
    }

    public void setDescription(List<JAXBElement<String>> description) {
        this.description = description;
    }

    public List<JAXBElement<String>> getDescription() {
        return description;
    }

}
