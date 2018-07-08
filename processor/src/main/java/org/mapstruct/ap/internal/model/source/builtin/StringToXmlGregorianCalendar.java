/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * @author Sjaak Derksen
 */
public class StringToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {

    private final Parameter parameter;
    private final Set<Type> importTypes;

    public StringToXmlGregorianCalendar(TypeFactory typeFactory) {
        super( typeFactory );
        this.parameter = new Parameter( "date", typeFactory.getType( String.class ) );
        this.importTypes = asSet(
            typeFactory.getType( GregorianCalendar.class ),
            typeFactory.getType( SimpleDateFormat.class ),
            typeFactory.getType( DateFormat.class ),
            typeFactory.getType( ParseException.class )
        );
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> result = super.getImportTypes();
        result.addAll( importTypes );
        return result;
    }

    @Override
    public Parameter getParameter() {
        return parameter;
    }

    @Override
    public String getContextParameter(ConversionContext conversionContext) {
        return conversionContext.getDateFormat() != null ? "\"" + conversionContext.getDateFormat() + "\"" : "null";
    }
}
