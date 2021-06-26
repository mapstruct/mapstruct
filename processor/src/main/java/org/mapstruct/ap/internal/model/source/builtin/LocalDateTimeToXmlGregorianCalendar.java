/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.XmlConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * @author Andrei Arlou
 */
public class LocalDateTimeToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {

    private final Parameter parameter;
    private final Set<Type> importTypes;

    public LocalDateTimeToXmlGregorianCalendar(TypeFactory typeFactory) {
        super( typeFactory );
        this.parameter = new Parameter( "localDateTime", typeFactory.getType( LocalDateTime.class ) );
        this.importTypes = asSet(
            parameter.getType(),
            typeFactory.getType( XmlConstants.JAVAX_XML_DATATYPE_CONSTANTS ),
            typeFactory.getType( ChronoField.class )
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
}
