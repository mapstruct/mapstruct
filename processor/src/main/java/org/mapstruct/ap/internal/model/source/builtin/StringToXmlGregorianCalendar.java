/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * @author Sjaak Derksen
 */
public class StringToXmlGregorianCalendar extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public StringToXmlGregorianCalendar(TypeFactory typeFactory) {
        this.parameter = new Parameter( "date", typeFactory.getType( String.class ) );
        this.returnType = typeFactory.getType( XMLGregorianCalendar.class );
        this.importTypes = asSet(
            returnType,
            typeFactory.getType( GregorianCalendar.class ),
            typeFactory.getType( SimpleDateFormat.class ),
            typeFactory.getType( DateFormat.class ),
            typeFactory.getType( ParseException.class ),
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

    @Override
    public String getContextParameter(ConversionContext conversionContext) {
        return conversionContext.getDateFormat() != null ? "\"" + conversionContext.getDateFormat() + "\"" : "null";
    }
}
