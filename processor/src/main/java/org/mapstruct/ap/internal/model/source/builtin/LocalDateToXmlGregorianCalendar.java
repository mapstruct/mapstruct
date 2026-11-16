/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.LocalDate;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.XmlConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * A built-in method for converting from {@link LocalDate} to {@code XMLGregorianCalendar}.
 *
 * @author Gunnar Morling
 */
public class LocalDateToXmlGregorianCalendar extends AbstractToXmlGregorianCalendar {

    private final Parameter parameter;
    private final Set<Type> importTypes;

    public LocalDateToXmlGregorianCalendar(TypeFactory typeFactory) {
        super( typeFactory );
        this.parameter = new Parameter( "localDate", typeFactory.getType( LocalDate.class ) );
        this.importTypes = asSet(
                parameter.getType(),
                typeFactory.getType( XmlConstants.JAVAX_XML_DATATYPE_CONSTANTS )
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
