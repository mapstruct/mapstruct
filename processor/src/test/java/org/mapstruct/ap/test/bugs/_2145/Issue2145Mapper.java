/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2145;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.namespace.QName;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper( uses = Issue2145Mapper.ObjectFactory.class )
public interface Issue2145Mapper {

    Issue2145Mapper INSTANCE = Mappers.getMapper( Issue2145Mapper.class );

    @Mapping(target = "nested", source = "value")
    Target map(Source source);

    default Nested map(String in) {
        Nested nested = new Nested();
        nested.setValue( in );
        return nested;
    }

    class Target {

        private JAXBElement<Nested> nested;

        public JAXBElement<Nested> getNested() {
            return nested;
        }

        public void setNested(JAXBElement<Nested> nested) {
            this.nested = nested;
        }
    }

    class Nested {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class Source {

        private String value;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    class ObjectFactory {

        private static final QName Q_NAME = new QName( "http://www.test.com/test", "" );
        private static final QName Q_NAME_NESTED = new QName( "http://www.test.com/test", "nested" );

        @XmlElementDecl(namespace = "http://www.test.com/test", name = "Nested")
        public JAXBElement<Nested> createNested(Nested value) {
            return new JAXBElement<Nested>( Q_NAME, Nested.class, null, value );
        }

        @XmlElementDecl(namespace = "http://www.test.com/test", name = "nested", scope = Nested.class)
        public JAXBElement<Nested> createNestedInNestedTarget(Nested value) {
            return new JAXBElement<Nested>( Q_NAME_NESTED, Nested.class, Target.class, value );
        }

    }
}
