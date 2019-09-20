/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * @author Andrei Arlou
 */
public class XmlGregorianCalendarToLocalDateTime extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public XmlGregorianCalendarToLocalDateTime(TypeFactory typeFactory) {
        this.parameter = new Parameter( "xcal", typeFactory.getType( XMLGregorianCalendar.class ) );
        this.returnType = typeFactory.getType( LocalDateTime.class );
        this.importTypes = asSet(
            returnType,
            parameter.getType(),
            typeFactory.getType( DatatypeConstants.class ),
            typeFactory.getType( Duration.class )
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
