/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
