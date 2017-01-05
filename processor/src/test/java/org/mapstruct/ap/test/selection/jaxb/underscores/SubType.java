/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
