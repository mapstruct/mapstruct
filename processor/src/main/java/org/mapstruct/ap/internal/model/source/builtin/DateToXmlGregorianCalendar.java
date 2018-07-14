/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * @author Sjaak Derksen
 */
public class DateToXmlGregorianCalendar extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public DateToXmlGregorianCalendar(TypeFactory typeFactory) {
        this.parameter = new Parameter( "date", typeFactory.getType( Date.class ) );
        this.returnType = typeFactory.getType( XMLGregorianCalendar.class );

        this.importTypes = asSet(
            returnType,
            parameter.getType(),
            typeFactory.getType( GregorianCalendar.class ),
            typeFactory.getType( DatatypeFactory.class ),
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
