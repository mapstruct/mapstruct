/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingMethodOptions;

import static org.mapstruct.ap.internal.util.Collections.asSet;

public class GetFieldAnnotation extends HelperMethod {

    private final Type returnType;
    private final List<Parameter> parameters;
    private final Set<Type> importTypes;

    public GetFieldAnnotation(TypeFactory typeFactory) {
        this.returnType = typeFactory.getType( Annotation.class );
        Parameter annotationClassParameter = new Parameter( "annotationClass", typeFactory.classTypeOf( returnType ) );
        Parameter fieldNameParameter = new Parameter( "fieldName", typeFactory.getType( String.class ) );
        this.parameters = Arrays.asList( annotationClassParameter, fieldNameParameter );
        this.importTypes = asSet( annotationClassParameter.getType(), fieldNameParameter.getType(), returnType );
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    @Override
    public Parameter getParameter() {
        throw new UnsupportedOperationException( "not used" );
    }

    @Override
    public List<Parameter> getParameters() {
        return parameters;
    }

    @Override
    public Type getReturnType() {
        return returnType;
    }

    @Override
    public MappingMethodOptions getOptions() {
        return MappingMethodOptions.empty();
    }

    @Override
    public String describe() {
        return null;
    }
}
