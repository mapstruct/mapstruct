/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.selection.jaxb.underscores;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "SubType", propOrder = { "declaredUnderscore" } )
public class SubType extends SuperType {

    @XmlElementRef( name = "declared_underscore",
            namespace = "http://www.mapstruct.org/itest/jaxb/xsd/underscores", type = JAXBElement.class )
    protected JAXBElement<String> declaredUnderscore;

    public JAXBElement<String> getDeclaredUnderscore() {
        return declaredUnderscore;
    }

    public void setDeclaredUnderscore(JAXBElement<String> value) {
        this.declaredUnderscore = value;
    }

    public boolean isSetDeclaredUnderscore() {
        return ( this.declaredUnderscore != null );
    }

}
