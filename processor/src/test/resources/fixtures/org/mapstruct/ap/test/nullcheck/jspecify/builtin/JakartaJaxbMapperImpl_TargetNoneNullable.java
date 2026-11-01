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
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.SomeType;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.SomeTypeProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.StringProperty;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-27T01:29:25+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class JakartaJaxbMapperImpl implements JakartaJaxbMapper {

    @Override
    public StringProperty map(JakartaJaxbElementProperty source) {
        if ( source == null ) {
            return null;
        }

        StringProperty stringProperty = new StringProperty();

        String prop = jaxbElemToValue( source.getProp() );
        if ( prop != null ) {
            stringProperty.setProp( prop );
        }
        String publicProp = jaxbElemToValue( source.publicProp );
        if ( publicProp != null ) {
            stringProperty.publicProp = publicProp;
        }

        return stringProperty;
    }

    @Override
    public BigDecimalProperty mapBD(JakartaJaxbElementProperty source) {
        if ( source == null ) {
            return null;
        }

        BigDecimalProperty bigDecimalProperty = new BigDecimalProperty();

        BigDecimal prop = jaxbElemToValueToBigDecimal( source.getProp() );
        if ( prop != null ) {
            bigDecimalProperty.setProp( prop );
        }
        BigDecimal publicProp = jaxbElemToValueToBigDecimal( source.publicProp );
        if ( publicProp != null ) {
            bigDecimalProperty.publicProp = publicProp;
        }

        return bigDecimalProperty;
    }

    @Override
    public SomeTypeProperty mapSomeType(JakartaJaxbElementProperty source) {
        if ( source == null ) {
            return null;
        }

        SomeTypeProperty someTypeProperty = new SomeTypeProperty();

        SomeType prop = map( jaxbElemToValue( source.getProp() ) );
        if ( prop != null ) {
            someTypeProperty.setProp( prop );
        }
        SomeType publicProp = map( jaxbElemToValue( source.publicProp ) );
        if ( publicProp != null ) {
            someTypeProperty.publicProp = publicProp;
        }

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
