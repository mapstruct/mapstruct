/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import static org.mapstruct.ap.internal.util.Collections.asSet;

import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JodaTimeConstants;

/**
 * @author Sjaak Derksen
 */
public class JodaLocalTimeToXmlGregorianCalendar extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public JodaLocalTimeToXmlGregorianCalendar(TypeFactory typeFactory) {
        this.parameter = new Parameter(
            "dt",
            typeFactory.getType( JodaTimeConstants.LOCAL_TIME_FQN )
        );
        this.returnType = typeFactory.getType( XMLGregorianCalendar.class );

        this.importTypes = asSet(
            returnType,
            parameter.getType(),
            typeFactory.getType( DatatypeConstants.class ),
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
