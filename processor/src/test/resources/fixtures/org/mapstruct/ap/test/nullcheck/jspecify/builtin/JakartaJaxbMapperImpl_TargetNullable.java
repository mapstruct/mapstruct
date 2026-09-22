/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import jakarta.xml.bind.JAXBElement;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JakartaJaxbElementProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.BigDecimalProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.SomeTypeProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.StringProperty;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-27T01:30:12+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class JakartaJaxbMapperImpl implements JakartaJaxbMapper {

    @Override
    public StringProperty map(JakartaJaxbElementProperty source) {
        if ( source == null ) {
            return null;
        }

        StringProperty stringProperty = new StringProperty();

        stringProperty.setProp( jaxbElemToValue( source.getProp() ) );
        stringProperty.publicProp = jaxbElemToValue( source.publicProp );

        return stringProperty;
    }

    @Override
    public BigDecimalProperty mapBD(JakartaJaxbElementProperty source) {
        if ( source == null ) {
            return null;
        }

        BigDecimalProperty bigDecimalProperty = new BigDecimalProperty();

        bigDecimalProperty.setProp( jaxbElemToValueToBigDecimal( source.getProp() ) );
        bigDecimalProperty.publicProp = jaxbElemToValueToBigDecimal( source.publicProp );

        return bigDecimalProperty;
    }

    @Override
    public SomeTypeProperty mapSomeType(JakartaJaxbElementProperty source) {
        if ( source == null ) {
            return null;
        }

        SomeTypeProperty someTypeProperty = new SomeTypeProperty();

        someTypeProperty.setProp( map( jaxbElemToValue( source.getProp() ) ) );
        someTypeProperty.publicProp = map( jaxbElemToValue( source.publicProp ) );

        return someTypeProperty;
    }

    private <T> T jaxbElemToValue( JAXBElement<T> element ) {
        if ( element == null ) {
            return null;
        }

        return element.isNil() ? null : element.getValue();
    }

    private BigDecimal jaxbElemToValueToBigDecimal(JAXBElement<String> element) {
        String string = jaxbElemToValue( element );
        if (string == null) {
            return null;
        }
        return new BigDecimal( string );
    }
}
