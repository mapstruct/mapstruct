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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.MapperPrism;
import org.mapstruct.ap.MappingPrism;
import org.mapstruct.ap.MappingsPrism;
import org.mapstruct.ap.model.ReportingPolicy;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.Parameter;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.TypeUtil;

import static javax.lang.model.util.ElementFilter.methodsIn;

/**
 * A {@link ModelElementProcessor} which retrieves a list of {@link Method}s
 * representing all the mapping methods of the given bean mapper type as well as
 * all referenced mapper methods declared by other mappers referenced by the
 * current mapper.
 *
 * @author Gunnar Morling
 */
public class MethodRetrievalProcessor implements ModelElementProcessor<TypeElement, List<Method>> {

    private Types typeUtils;
    private Messager messager;
    private TypeUtil typeUtil;
    private Executables executables;

    @Override
    public List<Method> process(ProcessorContext context, TypeElement mapperTypeElement, TypeElement sourceElement) {
        this.typeUtils = context.getTypeUtils();
        this.messager = context.getMessager();
        this.typeUtil = new TypeUtil( context.getElementUtils(), typeUtils );
        this.executables = new Executables( typeUtil );

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
     * @param implementationRequired Whether an implementation of this type must be generated or
     * not. {@code true} if the type is the currently processed
     * mapper interface, {@code false} if the given type is one
     * referred to via {@code Mapper#uses()}.
     *
     * @return All mapping methods declared by the given type
     */
    private List<Method> retrieveMethods(TypeElement element, boolean implementationRequired) {
        List<Method> methods = new ArrayList<Method>();

        MapperPrism mapperPrism = implementationRequired ? MapperPrism.getInstanceOn( element ) : null;

        for ( ExecutableElement executable : methodsIn( element.getEnclosedElements() ) ) {
            Method method = getMethod( element, executable, implementationRequired );
            if ( method != null ) {
                methods.add( method );
            }
        }

        //Add all methods of used mappers in order to reference them in the aggregated model
        if ( implementationRequired ) {
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

    private Method getMethod(TypeElement element, ExecutableElement method, boolean implementationRequired) {
        Parameter parameter = executables.retrieveParameter( method );
        Type returnType = executables.retrieveReturnType( method );

        //add method with property mappings if an implementation needs to be generated
        if ( implementationRequired ) {
            boolean isValid = checkParameterAndReturnType( method, parameter.getType(), returnType );

            if ( isValid ) {
                return
                    Method.forMethodRequiringImplementation(
                        method,
                        parameter.getName(),
                        parameter.getType(),
                        returnType,
                        getMappings( method )
                    );
            }
            else {
                return null;
            }
        }
        //otherwise add reference to existing mapper method
        else {
            return
                Method.forReferencedMethod(
                    typeUtil.getType( typeUtils.getDeclaredType( element ) ),
                    method,
                    parameter.getName(),
                    parameter.getType(),
                    returnType
                );
        }
    }

    private boolean checkParameterAndReturnType(ExecutableElement method, Type parameterType, Type returnType) {
        if ( parameterType.isIterableType() && !returnType.isIterableType() ) {
            printMessage(
                ReportingPolicy.ERROR,
                "Can't generate mapping method from iterable type to non-iterable type.",
                method
            );
            return false;
        }

        if ( !parameterType.isIterableType() && returnType.isIterableType() ) {
            printMessage(
                ReportingPolicy.ERROR,
                "Can't generate mapping method from non-iterable type to iterable type.",
                method
            );
            return false;
        }

        if ( parameterType.isPrimitive() ) {
            printMessage(
                ReportingPolicy.ERROR,
                "Can't generate mapping method with primitive parameter type.",
                method
            );
            return false;
        }

        if ( returnType.isPrimitive() ) {
            printMessage(
                ReportingPolicy.ERROR,
                "Can't generate mapping method with primitive return type.",
                method
            );
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
            mappings.put( mappingAnnotation.source(), Mapping.fromMappingPrism( mappingAnnotation ) );
        }

        if ( mappingsAnnotation != null ) {
            mappings.putAll( Mapping.fromMappingsPrism( mappingsAnnotation ) );
        }

        return mappings;
    }

    private void printMessage(ReportingPolicy reportingPolicy, String message, Element element) {
        messager.printMessage( reportingPolicy.getDiagnosticKind(), message, element );
    }
}
