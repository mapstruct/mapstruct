/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import javax.xml.bind.JAXBElement;
import org.jspecify.annotations.NonNull;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.sources.JaxbElementListProperty;
import org.mapstruct.ap.test.nullcheck.jspecify.builtin.targets.StringListProperty;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-08-27T22:26:01+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
)
public class JaxbListMapperImpl implements JaxbListMapper {

    @Override
    public StringListProperty map(JaxbElementListProperty source) {
        if ( source == null ) {
            return null;
        }

        StringListProperty stringListProperty = new StringListProperty();

        stringListProperty.setProp( jAXBElementListToStringList( source.getProp() ) );
        stringListProperty.publicProp = jAXBElementListToStringList( source.publicProp );

        return stringListProperty;
    }

    private <T> T jaxbElemToValue( JAXBElement<T> element ) {
        if ( element == null ) {
            return null;
        }

        return element.isNil() ? null : element.getValue();
    }

    protected @NonNull List<String> jAXBElementListToStringList(@NonNull List<JAXBElement<String>> list) {

        List<String> list1 = new ArrayList<>( list.size() );
        for ( JAXBElement<String> jAXBElement : list ) {
            list1.add( jaxbElemToValue( jAXBElement ) );
        }

        return list1;
    }
}
