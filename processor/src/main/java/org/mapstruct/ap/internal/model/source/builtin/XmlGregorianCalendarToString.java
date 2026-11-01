/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.ConversionContext;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.XmlConstants;
import org.mapstruct.ap.internal.util.accessor.Nullability;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * A built-in method for converting from {@code XMLGregorianCalendar} to {@link String}.
 *
 * @author Sjaak Derksen
 */
public class XmlGregorianCalendarToString extends BuiltInMethod {

    private final Parameter parameter;
    private final Parameter dateFormatParameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public XmlGregorianCalendarToString(TypeFactory typeFactory) {
        this.parameter = new Parameter( "xcal", typeFactory.getType( XmlConstants.JAVAX_XML_XML_GREGORIAN_CALENDAR ),
                Nullability.hardcodedNullability( Nullability.NullabilityState.NON_NULL ) );
        this.dateFormatParameter = new Parameter( "dateFormat", typeFactory.getType( String.class ),
                Nullability.hardcodedNullability( Nullability.NullabilityState.NULLABLE ) );
        this.returnType = typeFactory.getType( String.class );
        this.importTypes = asSet(
            parameter.getType(),
            typeFactory.getType( Date.class ),
            typeFactory.getType( SimpleDateFormat.class )
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
    public List<Parameter> getAuxiliaryParameters() {
        return Collections.singletonList( dateFormatParameter );
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public String getContextParameter(ConversionContext conversionContext) {
        return conversionContext.getDateFormat() != null ? "\"" + conversionContext.getDateFormat() + "\"" : "null";
    }

    @Override
    public Nullability getReturnTypeNullability() {
        return Nullability.hardcodedNullability( Nullability.NullabilityState.NON_NULL );
    }

}
