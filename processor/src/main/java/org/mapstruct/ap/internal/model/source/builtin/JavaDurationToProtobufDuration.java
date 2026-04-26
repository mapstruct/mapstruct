/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.Duration;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.ProtobufConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Converts {@link Duration} to {@code com.google.protobuf.Duration}.
 *
 * @author Freeman
 */
public class JavaDurationToProtobufDuration extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public JavaDurationToProtobufDuration(TypeFactory typeFactory) {
        this.parameter = new Parameter( "duration", typeFactory.getType( Duration.class ) );
        this.returnType = typeFactory.getType( ProtobufConstants.DURATION_FQN );
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
