/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.time.LocalDate;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * @author Gunnar Morling
 */
public class LocalDateToXmlGregorianCalendar extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public LocalDateToXmlGregorianCalendar(TypeFactory typeFactory) {
        this.parameter = new Parameter( "localDate", typeFactory.getType( LocalDate.class ) );
        this.returnType = typeFactory.getType( XMLGregorianCalendar.class );
        this.importTypes = asSet(
                returnType,
                parameter.getType(),
                typeFactory.getType( DatatypeFactory.class ),
                typeFactory.getType( DatatypeConfigurationException.class ),
                typeFactory.getType( DatatypeConstants.class )
        );
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }
}
