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
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

@XmlRegistry
public class ObjectFactory {

    private static final QName SUPER_QNAME =
        new QName( "http://www.mapstruct.org/itest/jaxb/xsd/underscores", "Super" );
    private static final QName SUB_QNAME = new QName( "http://www.mapstruct.org/itest/jaxb/xsd/underscores", "Sub" );
    private static final QName SUPER_TYPE_INHERITED_UNDERSCORE_QNAME =
        new QName( "http://www.mapstruct.org/itest/jaxb/xsd/underscores", "inherited_underscore" );
    private static final QName SUB_TYPE_DECLARED_UNDERSCORE_QNAME =
        new QName( "http://www.mapstruct.org/itest/jaxb/xsd/underscores", "declared_underscore" );

    public ObjectFactory() {
    }

    public SubType createSubType() {
        return new SubType();
    }

    public SuperType createSuperType() {
        return new SuperType();
    }

    @XmlElementDecl( namespace = "http://www.mapstruct.org/itest/jaxb/xsd/underscores", name = "Super" )
    public JAXBElement<SuperType> createSuper(SuperType value) {
        return new JAXBElement<SuperType>(SUPER_QNAME, SuperType.class, null, value );
    }

    @XmlElementDecl( namespace = "http://www.mapstruct.org/itest/jaxb/xsd/underscores", name = "Sub" )
    public JAXBElement<SubType> createSub(SubType value) {
        return new JAXBElement<SubType>(SUB_QNAME, SubType.class, null, value );
    }

    @XmlElementDecl( namespace = "http://www.mapstruct.org/itest/jaxb/xsd/underscores",
            name = "inherited_underscore", scope = SuperType.class )
    public JAXBElement<String> createSuperTypeInheritedUnderscore(String value) {
        return new JAXBElement<String>(SUPER_TYPE_INHERITED_UNDERSCORE_QNAME, String.class, SuperType.class, value );
    }

    @XmlElementDecl( namespace = "http://www.mapstruct.org/itest/jaxb/xsd/underscores",
            name = "declared_underscore", scope = SubType.class )
    public JAXBElement<String> createSubTypeDeclaredUnderscore(String value) {
        return new JAXBElement<String>(SUB_TYPE_DECLARED_UNDERSCORE_QNAME, String.class, SubType.class, value );
    }
}
