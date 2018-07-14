/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.ZonedDateTime;
import java.util.GregorianCalendar;
import java.util.Set;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * @author Christian Bandowski
 */
public class ZonedDateTimeToXmlGregorianCalendar extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public ZonedDateTimeToXmlGregorianCalendar(TypeFactory typeFactory) {
        this.parameter = new Parameter( "zdt ", typeFactory.getType( ZonedDateTime.class ) );
        this.returnType = typeFactory.getType( XMLGregorianCalendar.class );

        this.importTypes = asSet(
            returnType,
            parameter.getType(),
            typeFactory.getType( DatatypeFactory.class ),
            typeFactory.getType( GregorianCalendar.class ),
            typeFactory.getType( DatatypeConfigurationException.class )
        );
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }
}
