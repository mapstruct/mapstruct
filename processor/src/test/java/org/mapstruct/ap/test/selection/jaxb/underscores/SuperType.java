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
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType( XmlAccessType.FIELD )
@XmlType( name = "SuperType", propOrder = { "inheritedUnderscore" } )
@XmlSeeAlso( { SubType.class } )
public class SuperType {

    @XmlElementRef( name = "inherited_underscore",
            namespace = "http://www.mapstruct.org/itest/jaxb/xsd/underscores", type = JAXBElement.class )
    protected JAXBElement<String> inheritedUnderscore;

    public JAXBElement<String> getInheritedUnderscore() {
        return inheritedUnderscore;
    }

    public void setInheritedUnderscore(JAXBElement<String> value) {
        this.inheritedUnderscore = value;
    }

    public boolean isSetInheritedUnderscore() {
        return ( this.inheritedUnderscore != null );
    }

}
