/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.jspecify.builtintomethod;

import javax.annotation.processing.Generated;
import javax.xml.bind.JAXBElement;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-09-14T22:19:53+0200",
    comments = "version: , compiler: javac, environment: Java 21.0.12.1 (Eclipse Adoptium)"
)
public class BuiltInMethodMapperImpl implements BuiltInMethodMapper {

    @Override
    public BuiltInMethodTargetBean map(BuiltInMethodSourceBean source) {
        if ( source == null ) {
            return null;
        }

        BuiltInMethodTargetBean builtInMethodTargetBean = new BuiltInMethodTargetBean();

        builtInMethodTargetBean.setLength( jaxbElemToValueToint( source.getElem() ) );

        return builtInMethodTargetBean;
    }

    private <T> T jaxbElemToValue( JAXBElement<T> element ) {
        if ( element == null ) {
            return null;
        }

        return element.isNil() ? null : element.getValue();
    }

    private int jaxbElemToValueToint(JAXBElement<String> element) {
        String jAXBElement = jaxbElemToValue( element );
        if (jAXBElement == null) {
            return 0;
        }
        return toLength( jAXBElement );
    }
}
