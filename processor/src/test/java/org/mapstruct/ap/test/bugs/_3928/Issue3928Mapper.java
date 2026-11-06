/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3928;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = Issue3928Mapper.ObjectFactory.class)
public interface Issue3928Mapper {

    Issue3928Mapper INSTANCE = Mappers.getMapper( Issue3928Mapper.class );

    String NAMESPACE = "http://www.mapstruct.org/itest/jaxb/xsd/issue3928";

    Target map(Source source);

    class Source {
        private String xyFirst;
        private String xySecond;

        public String getXYFirst() {
            return xyFirst;
        }

        public void setXYFirst(String xyFirst) {
            this.xyFirst = xyFirst;
        }

        public String getXYSecond() {
            return xySecond;
        }

        public void setXYSecond(String xySecond) {
            this.xySecond = xySecond;
        }
    }

    /**
     * What xjc and cxf generate for the elements {@code XY-First} and {@code XY-Second}: the field starts with a lower
     * case word, whereas the accessors, and so the MapStruct property, keep the upper case {@code XY}.
     */
    class Target {
        @XmlElementRef(name = "XY-First", namespace = NAMESPACE, type = JAXBElement.class, required = false)
        protected JAXBElement<String> xyFirst;

        @XmlElementRef(name = "XY-Second", namespace = NAMESPACE, type = JAXBElement.class, required = false)
        protected JAXBElement<String> xySecond;

        public JAXBElement<String> getXYFirst() {
            return xyFirst;
        }

        public void setXYFirst(JAXBElement<String> xyFirst) {
            this.xyFirst = xyFirst;
        }

        public JAXBElement<String> getXYSecond() {
            return xySecond;
        }

        public void setXYSecond(JAXBElement<String> xySecond) {
            this.xySecond = xySecond;
        }
    }

    @XmlRegistry
    class ObjectFactory {
        static final QName XY_FIRST_QNAME = new QName( NAMESPACE, "XY-First" );
        static final QName XY_SECOND_QNAME = new QName( NAMESPACE, "XY-Second" );

        @XmlElementDecl(namespace = NAMESPACE, name = "XY-First", scope = Target.class)
        public JAXBElement<String> createTargetXYFirst(String value) {
            return new JAXBElement<>( XY_FIRST_QNAME, String.class, Target.class, value );
        }

        @XmlElementDecl(namespace = NAMESPACE, name = "XY-Second", scope = Target.class)
        public JAXBElement<String> createTargetXYSecond(String value) {
            return new JAXBElement<>( XY_SECOND_QNAME, String.class, Target.class, value );
        }
    }
}
