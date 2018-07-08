/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.util.Set;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * @author Sjaak Derksen
 */
public class XmlGregorianCalendarToJodaLocalDate extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public XmlGregorianCalendarToJodaLocalDate(TypeFactory typeFactory) {
        this.parameter = new Parameter( "xcal", typeFactory.getType( XMLGregorianCalendar.class ) );
        this.returnType = typeFactory.getType( JodaTimeConstants.LOCAL_DATE_FQN );
        this.importTypes = asSet(
            typeFactory.getType( DatatypeConstants.class ),
            returnType,
            parameter.getType() );
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
