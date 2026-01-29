/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.builtin;

import java.time.Instant;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.ProtobufConstants;

import static org.mapstruct.ap.internal.util.Collections.asSet;

/**
 * Converts {@code com.google.protobuf.Timestamp} to {@link Instant}.
 *
 * @author Freeman
 */
public class ProtobufTimestampToInstant extends BuiltInMethod {

    private final Parameter parameter;
    private final Type returnType;
    private final Set<Type> importTypes;

    public ProtobufTimestampToInstant(TypeFactory typeFactory) {
        this.parameter = new Parameter( "timestamp", typeFactory.getType( ProtobufConstants.TIMESTAMP_FQN ) );
        this.returnType = typeFactory.getType( Instant.class );
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
