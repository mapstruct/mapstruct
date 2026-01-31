/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.util.Calendar;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.XmlConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * A built-in method for converting from {@code XMLGregorianCalendar} to {@link Calendar}.
 *
 * @author Sjaak Derksen
 */
public class XmlGregorianCalendarToCalendar extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public XmlGregorianCalendarToCalendar(TypeFactory typeFactory) {
        this.parameter = new Parameter( "xcal", typeFactory.getType( XmlConstants.JAVAX_XML_XML_GREGORIAN_CALENDAR ) );
        this.returnType = typeFactory.getType( Calendar.class );
        this.importTypes = asSet( returnType, parameter.getType() );
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
