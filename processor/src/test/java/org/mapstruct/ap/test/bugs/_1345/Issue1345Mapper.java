/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1345;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 *
 * @author Sjaak Derksen
 */
@Mapper
public interface Issue1345Mapper {

    Issue1345Mapper INSTANCE = Mappers.getMapper( Issue1345Mapper.class );

    @Mapping(target = "property", source = "readOnlyProperty")
    B a2B(A a);

    @InheritInverseConfiguration(name = "a2B")
    A b2A(B b);

    class A {

        private String value;
        private String readOnlyProperty;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getReadOnlyProperty() {
            return readOnlyProperty;
        }
    }

    class B {

        private String value;
        private String property;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }
    }
}
