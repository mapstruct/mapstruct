/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Set;
import java.util.TimeZone;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.JavaTimeConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * {@link BuiltInMethod} for mapping between {@link Calendar} and {@link ZonedDateTime}.
 * <p>
 * Template is at org.mapstruct.ap.model.builtin.ZonedDateTimeToCalendar.ftl
 */
public class ZonedDateTimeToCalendar extends BuiltInMethod {
    private final Type returnType;
    private final Parameter parameter;
    private final Set<Type> importedTypes;

    ZonedDateTimeToCalendar(TypeFactory typeFactory) {
        this.returnType = typeFactory.getType( Calendar.class );
        this.parameter = new Parameter( "dateTime", typeFactory.getType( JavaTimeConstants.ZONED_DATE_TIME_FQN ) );
        this.importedTypes = asSet( returnType, parameter.getType(), typeFactory.getType( TimeZone.class ) );
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
        return importedTypes;
    }
}
