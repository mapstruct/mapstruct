/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.accessor.Nullability;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * A built-in method for converting from {@link String} to {@code XMLGregorianCalendar}.
 *
 * @author Sjaak Derksen
 */
public class StringToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {

    private final Parameter parameter;
    private final Parameter dateFormatParameter;
    private final Set<Type> importTypes;

    public StringToXmlGregorianCalendar(TypeFactory typeFactory) {
        super( typeFactory );
        this.parameter = new Parameter( "date", typeFactory.getType( String.class ),
                Nullability.hardcodedNullability( Nullability.NullabilityState.NON_NULL ) );
        this.dateFormatParameter = new Parameter( "dateFormat", typeFactory.getType( String.class ),
                Nullability.hardcodedNullability( Nullability.NullabilityState.NULLABLE ) );
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
    public List<Parameter> getAuxiliaryParameters() {
        return Collections.singletonList( dateFormatParameter );
    }

    @Override
    public String getContextParameter(ConversionContext conversionContext) {
        return conversionContext.getDateFormat() != null ? "\"" + conversionContext.getDateFormat() + "\"" : "null";
    }
}
