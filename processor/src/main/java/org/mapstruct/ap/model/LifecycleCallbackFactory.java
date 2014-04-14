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

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeKind;

import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;

/**
 * Factory for creating lists of appropriate {@link LifecycleCallbackMethodReference}s
 *
 * @author Andreas Gudian
 */
public final class LifecycleCallbackFactory {

    private LifecycleCallbackFactory() {
    }

    /**
     * @param method the method to obtain the beforeMapping methods for
     * @param ctx the builder context
     * @return all applicable {@code @BeforeMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> beforeMappingMethods(
            Method method, MappingBuilderContext ctx) {
        return collectLifecycleCallbackMethods( method, filterBeforeMappingMethods( ctx.getSourceModel() ), ctx );
    }

    /**
     * @param method the method to obtain the afterMapping methods for
     * @param ctx the builder context
     * @return all applicable {@code @AfterMapping} methods for the given method
     */
    public static List<LifecycleCallbackMethodReference> afterMappingMethods(
            Method method, MappingBuilderContext ctx) {
        return collectLifecycleCallbackMethods( method, filterAfterMappingMethods( ctx.getSourceModel() ), ctx );
    }

    private static List<LifecycleCallbackMethodReference> collectLifecycleCallbackMethods(
            Method method, List<SourceMethod> callbackMethods, MappingBuilderContext ctx) {

        List<Parameter> availableParams = new ArrayList<Parameter>( method.getParameters() );
        if ( method.getMappingTargetParameter() == null ) {
            availableParams.add( new Parameter( null, method.getResultType(), true, false ) );
        }

        Parameter targetTypeParameter = new Parameter(
            null,
            ctx.getTypeFactory().classTypeOf( method.getResultType() ),
            false,
            true );

        availableParams.add( targetTypeParameter );

        List<LifecycleCallbackMethodReference> result = new ArrayList<LifecycleCallbackMethodReference>();

        for ( SourceMethod callback : callbackMethods ) {
            List<Parameter> parameterAssignments = getParameterAssignments( availableParams, callback.getParameters() );

            if ( parameterAssignments != null
                && callback.matches( extractSourceTypes( parameterAssignments ), method.getResultType() ) ) {

                markMapperReferenceAsUsed( ctx.getMapperReferences(), callback );
                result.add( new LifecycleCallbackMethodReference( callback, parameterAssignments ) );
            }
        }
        return result;
    }

    private static void markMapperReferenceAsUsed(List<MapperReference> references, SourceMethod method) {
        for ( MapperReference ref : references ) {
            if ( ref.getType().equals( method.getDeclaringMapper() ) ) {
                ref.setUsed( !method.isStatic() );
                ref.setTypeRequiresImport( true );

                return;
            }
        }
    }

    private static List<Type> extractSourceTypes(List<Parameter> parameters) {
        List<Type> result = new ArrayList<Type>( parameters.size() );

        for ( Parameter param : parameters ) {
            if ( !param.isMappingTarget() && !param.isTargetType() ) {
                result.add( param.getType() );
            }
        }

        return result;
    }

    private static List<Parameter> getParameterAssignments(
            List<Parameter> availableParams, List<Parameter> methodParameters) {
        List<Parameter> result = new ArrayList<Parameter>( methodParameters.size() );

        for ( Parameter methodParam : methodParameters ) {
            List<Parameter> assignableParams = findCandidateParameters( availableParams, methodParam );

            if ( assignableParams.isEmpty() ) {
                return null;
            }

            if ( assignableParams.size() == 1 ) {
                result.add( assignableParams.get( 0 ) );
            }
            else if ( assignableParams.size() > 1 ) {
                Parameter paramWithMatchingName = findParameterWithName( assignableParams, methodParam.getName() );

                if ( paramWithMatchingName != null ) {
                    result.add( paramWithMatchingName );
                }
                else {
                    return null;
                }
            }
        }

        return result;
    }

    private static Parameter findParameterWithName(List<Parameter> parameters, String name) {
        for ( Parameter param : parameters ) {
            if ( name.equals( param.getName() ) ) {
                return param;
            }
        }

        return null;
    }

    private static List<Parameter> findCandidateParameters(List<Parameter> candiateParameters, Parameter parameter) {
        List<Parameter> result = new ArrayList<Parameter>( candiateParameters.size() );

        for ( Parameter candidate : candiateParameters ) {
            if ( ( isTypeVarOrWildcard( parameter ) || candidate.getType().isAssignableTo( parameter.getType() ) )
                && parameter.isMappingTarget() == candidate.isMappingTarget()
                && !parameter.isTargetType() && !candidate.isTargetType() ) {
                result.add( candidate );
            }
            else if ( parameter.isTargetType() && candidate.isTargetType() ) {
                result.add( candidate );
            }
        }

        return result;
    }

    private static boolean isTypeVarOrWildcard(Parameter parameter) {
        TypeKind kind = parameter.getType().getTypeMirror().getKind();
        return kind == TypeKind.TYPEVAR || kind == TypeKind.WILDCARD;
    }

    private static List<SourceMethod> filterBeforeMappingMethods(List<SourceMethod> methods) {
        List<SourceMethod> result = new ArrayList<SourceMethod>();
        for ( SourceMethod method : methods ) {
            if ( method.isBeforeMappingMethod() ) {
                result.add( method );
            }
        }

        return result;
    }

    private static List<SourceMethod> filterAfterMappingMethods(List<SourceMethod> methods) {
        List<SourceMethod> result = new ArrayList<SourceMethod>();
        for ( SourceMethod method : methods ) {
            if ( method.isAfterMappingMethod() ) {
                result.add( method );
            }
        }

        return result;
    }
}
