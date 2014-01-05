/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.processor;

import static javax.lang.model.util.ElementFilter.methodsIn;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.AnnotationProcessingException;
import org.mapstruct.ap.IterableMappingPrism;
import org.mapstruct.ap.MapMappingPrism;
import org.mapstruct.ap.MapperPrism;
import org.mapstruct.ap.MappingPrism;
import org.mapstruct.ap.MappingsPrism;
import org.mapstruct.ap.model.Parameter;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.source.IterableMapping;
import org.mapstruct.ap.model.source.MapMapping;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.TypeFactory;

/**
 * A {@link ModelElementProcessor} which retrieves a list of {@link Method}s
 * representing all the mapping methods of the given bean mapper type as well as
 * all referenced mapper methods declared by other mappers referenced by the
 * current mapper.
 *
 * @author Gunnar Morling
 */
public class MethodRetrievalProcessor implements ModelElementProcessor<Void, List<Method>> {

    private Messager messager;
    private TypeFactory typeFactory;
    private Executables executables;

    @Override
    public List<Method> process(ProcessorContext context, TypeElement mapperTypeElement, Void sourceModel) {
        this.messager = context.getMessager();
        this.typeFactory = context.getTypeFactory();
        this.executables = new Executables( typeFactory );

        return retrieveMethods( mapperTypeElement, true );
    }

    @Override
    public int getPriority() {
        return 1;
    }

    /**
     * Retrieves the mapping methods declared by the given mapper type.
     *
     * @param element The type of interest
     * @param mapperRequiresImplementation Whether an implementation of this type must be generated or not. {@code true}
     * if the type is the currently processed mapper interface, {@code false} if the given type is one
     * referred to via {@code Mapper#uses()}.
     *
     * @return All mapping methods declared by the given type
     */
    private List<Method> retrieveMethods(TypeElement element, boolean mapperRequiresImplementation) {
        List<Method> methods = new ArrayList<Method>();

        for ( ExecutableElement executable : methodsIn( element.getEnclosedElements() ) ) {
            Method method = getMethod( element, executable, mapperRequiresImplementation );
            if ( method != null ) {
                methods.add( method );
            }
        }

        //Add all methods of used mappers in order to reference them in the aggregated model
        if ( mapperRequiresImplementation ) {
            MapperPrism mapperPrism = MapperPrism.getInstanceOn( element );
            if ( !mapperPrism.isValid ) {
                throw new AnnotationProcessingException(
                    "Couldn't retrieve @Mapper annotation", element, mapperPrism.mirror
                );
            }

            for ( TypeMirror usedMapper : mapperPrism.uses() ) {
                methods.addAll(
                    retrieveMethods(
                        (TypeElement) ( (DeclaredType) usedMapper ).asElement(),
                        false
                    )
                );
            }
        }

        return methods;
    }

    private Method getMethod(TypeElement element, ExecutableElement method, boolean mapperRequiresImplementation) {
        List<Parameter> parameters = executables.retrieveParameters( method );
        Type returnType = executables.retrieveReturnType( method );

        //add method with property mappings if an implementation needs to be generated
        boolean methodRequiresImplementation = method.getModifiers().contains( Modifier.ABSTRACT );

        if ( mapperRequiresImplementation && methodRequiresImplementation ) {
            List<Parameter> sourceParameters = extractSourceParameters( parameters );
            Parameter targetParameter = extractTargetParameter( parameters );
            Type resultType = selectResultType( returnType, targetParameter );

            boolean isValid =
                checkParameterAndReturnType( method, sourceParameters, targetParameter, resultType, returnType );

            if ( isValid ) {
                return
                    Method.forMethodRequiringImplementation(
                        method,
                        parameters,
                        returnType,
                        getMappings( method ),
                        IterableMapping.fromPrism( IterableMappingPrism.getInstanceOn( method ) ),
                        MapMapping.fromPrism( MapMappingPrism.getInstanceOn( method ) )
                    );
            }
            else {
                return null;
            }
        }
        //otherwise add reference to existing mapper method
        else if ( parameters.size() == 1 ) {
            return
                Method.forReferencedMethod(
                    mapperRequiresImplementation ? null : typeFactory.getType( element ),
                    method,
                    parameters,
                    returnType
                );
        }
        else {
            return null;
        }
    }

    private Parameter extractTargetParameter(List<Parameter> parameters) {
        for ( Parameter param : parameters ) {
            if ( param.isMappingTarget() ) {
                return param;
            }
        }

        return null;
    }

    private List<Parameter> extractSourceParameters(List<Parameter> parameters) {
        List<Parameter> sourceParameters = new ArrayList<Parameter>( parameters.size() );
        for ( Parameter param : parameters ) {
            if ( !param.isMappingTarget() ) {
                sourceParameters.add( param );
            }
        }

        return sourceParameters;
    }

    private Type selectResultType(Type returnType, Parameter targetParameter) {
        if ( null != targetParameter ) {
            return targetParameter.getType();
        }
        else {
            return returnType;
        }
    }

    private boolean checkParameterAndReturnType(ExecutableElement method, List<Parameter> sourceParameters,
                                                Parameter targetParameter, Type resultType, Type returnType) {
        if ( sourceParameters.isEmpty() ) {
            messager.printMessage( Kind.ERROR, "Can't generate mapping method with no input arguments.", method );
            return false;
        }

        if ( targetParameter != null && ( sourceParameters.size() + 1 != method.getParameters().size() ) ) {
            messager.printMessage(
                Kind.ERROR,
                "Can't generate mapping method with more than one @MappingTarget parameter.",
                method
            );
            return false;
        }

        if ( resultType.getTypeMirror().getKind() == TypeKind.VOID ) {
            messager.printMessage( Kind.ERROR, "Can't generate mapping method with return type void.", method );
            return false;
        }

        if ( returnType.getTypeMirror().getKind() != TypeKind.VOID &&
            !resultType.isAssignableTo( returnType ) ) {
            messager.printMessage(
                Kind.ERROR,
                "The result type is not assignable to the the return type.",
                method
            );
            return false;
        }

        Type parameterType = sourceParameters.get( 0 ).getType();

        if ( parameterType.isIterableType() && !resultType.isIterableType() ) {
            messager.printMessage(
                Kind.ERROR,
                "Can't generate mapping method from iterable type to non-iterable type.",
                method
            );
            return false;
        }

        if ( !parameterType.isIterableType() && resultType.isIterableType() ) {
            messager.printMessage(
                Kind.ERROR,
                "Can't generate mapping method from non-iterable type to iterable type.",
                method
            );
            return false;
        }

        if ( parameterType.isPrimitive() ) {
            messager.printMessage( Kind.ERROR, "Can't generate mapping method with primitive parameter type.", method );
            return false;
        }

        if ( resultType.isPrimitive() ) {
            messager.printMessage( Kind.ERROR, "Can't generate mapping method with primitive return type.", method );
            return false;
        }

        return true;
    }

    /**
     * Retrieves the mappings configured via {@code @Mapping} from the given
     * method.
     *
     * @param method The method of interest
     *
     * @return The mappings for the given method, keyed by source property name
     */
    private Map<String, Mapping> getMappings(ExecutableElement method) {
        Map<String, Mapping> mappings = new HashMap<String, Mapping>();

        MappingPrism mappingAnnotation = MappingPrism.getInstanceOn( method );
        MappingsPrism mappingsAnnotation = MappingsPrism.getInstanceOn( method );

        if ( mappingAnnotation != null ) {
            mappings.put( mappingAnnotation.source(), Mapping.fromMappingPrism( mappingAnnotation, method ) );
        }

        if ( mappingsAnnotation != null ) {
            mappings.putAll( Mapping.fromMappingsPrism( mappingsAnnotation, method ) );
        }

        return mappings;
    }
}
