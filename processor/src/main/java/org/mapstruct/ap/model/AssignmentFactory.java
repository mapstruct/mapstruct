/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.model;

import java.util.List;
import java.util.Set;

import javax.tools.Diagnostic;

import org.mapstruct.ap.model.assignment.Assignment;
import org.mapstruct.ap.model.common.ConversionContext;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.model.source.builtin.BuiltInMethod;
import org.mapstruct.ap.model.source.selector.MethodSelectors;

/**
 * Factory class for creating all types of assignments
 *
 * @author Sjaak Derksen
 */
public class AssignmentFactory {

    private AssignmentFactory() {
    }

    public static Assignment createTypeConversion(Set<Type> importTypes, List<Type> exceptionTypes, String expression) {
        return new TypeConversion( importTypes, exceptionTypes, expression );
    }

    public static Assignment createMethodReference(Method method, MapperReference declaringMapper,
                                                   Type targetType) {
        return new MethodReference( method, declaringMapper, targetType );
    }

    public static Assignment createMethodReference(BuiltInMethod method, ConversionContext contextParam) {
        return new MethodReference( method, contextParam );
    }

    public static Direct createDirect(String sourceRef) {
        return new Direct( sourceRef );
    }

    public static MethodReference createFactoryMethod( Type returnType, MappingBuilderContext ctx ) {
        MethodReference result = null;
        for ( SourceMethod method : ctx.getSourceModel() ) {
            if ( !method.overridesMethod() && !method.isIterableMapping() && !method.isMapMapping()
                    && method.getSourceParameters().isEmpty() ) {

                List<Type> parameterTypes = MethodSelectors.getParameterTypes(
                        ctx.getTypeFactory(),
                        method.getParameters(),
                        null,
                        returnType
                );

                if ( method.matches( parameterTypes, returnType ) ) {
                    if ( result == null ) {
                        MapperReference mapperReference = findMapperReference( ctx.getMapperReferences(), method );
                        result = new MethodReference( method, mapperReference, null );
                    }
                    else {
                        ctx.getMessager().printMessage(
                                Diagnostic.Kind.ERROR,
                                String.format(
                                        "Ambiguous factory methods: \"%s\" conflicts with \"%s\".",
                                        result,
                                        method
                                ),
                                method.getExecutable()
                        );
                    }
                }
            }
        }
        return result;
    }

    private static MapperReference findMapperReference( List<MapperReference> mapperReferences, SourceMethod method ) {
        for ( MapperReference ref : mapperReferences ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                return ref;
            }
        }
        return null;
    }
}

