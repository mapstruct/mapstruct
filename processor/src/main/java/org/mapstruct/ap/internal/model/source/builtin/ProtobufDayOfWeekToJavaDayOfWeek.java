/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.DayOfWeek;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.ProtobufConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Converts {@link com.google.type.DayOfWeek} to {@link DayOfWeek}.
 *
 * @author Freeman
 */
public class ProtobufDayOfWeekToJavaDayOfWeek extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public ProtobufDayOfWeekToJavaDayOfWeek(TypeFactory typeFactory) {
        this.parameter = new Parameter( "dayOfWeek", typeFactory.getType( ProtobufConstants.DAY_OF_WEEK_FQN ) );
        this.returnType = typeFactory.getType( DayOfWeek.class );
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
