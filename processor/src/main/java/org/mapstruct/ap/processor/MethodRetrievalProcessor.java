/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic.Kind;

import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.common.TypeFactory;
import org.mapstruct.ap.model.source.IterableMapping;
import org.mapstruct.ap.model.source.MapMapping;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.prism.IterableMappingPrism;
import org.mapstruct.ap.prism.MapMappingPrism;
import org.mapstruct.ap.prism.MappingPrism;
import org.mapstruct.ap.prism.MappingsPrism;
import org.mapstruct.ap.util.AnnotationProcessingException;
import org.mapstruct.ap.util.MapperConfig;

/**
 * A {@link ModelElementProcessor} which retrieves a list of {@link SourceMethod}s
 * representing all the mapping methods of the given bean mapper type as well as
 * all referenced mapper methods declared by other mappers referenced by the
 * current mapper.
 *
 * @author Gunnar Morling
 */
public class MethodRetrievalProcessor implements ModelElementProcessor<Void, List<SourceMethod>> {

    private Messager messager;
    private TypeFactory typeFactory;
    private Types typeUtils;

    @Override
    public List<SourceMethod> process(ProcessorContext context, TypeElement mapperTypeElement, Void sourceModel) {
        this.messager = context.getMessager();
        this.typeFactory = context.getTypeFactory();
        this.typeUtils = context.getTypeUtils();
        return retrieveMethods( mapperTypeElement, mapperTypeElement );
    }

    @Override
    public int getPriority() {
        return 1;
    }

    /**
     * Retrieves the mapping methods declared by the given mapper type.
     *
     * @param usedMapper The type of interest (either the mapper to implement or a used mapper via @uses annotation)
     * @param mapperToImplement the top level type (mapper) that requires implementation
     *
     * @return All mapping methods declared by the given type
     */
    private List<SourceMethod> retrieveMethods(TypeElement usedMapper, TypeElement mapperToImplement) {
        List<SourceMethod> methods = new ArrayList<SourceMethod>();

        for ( ExecutableElement executable : methodsIn( allEnclosingElementsIncludeSuper( usedMapper ) ) ) {
            SourceMethod method = getMethod( usedMapper, executable, mapperToImplement );
            if ( method != null ) {
                methods.add( method );
            }
        }

        //Add all methods of used mappers in order to reference them in the aggregated model
        if ( usedMapper.equals( mapperToImplement ) ) {
            MapperConfig mapperSettings = MapperConfig.getInstanceOn( usedMapper );
            if ( !mapperSettings.isValid() ) {
                throw new AnnotationProcessingException(
                    "Couldn't retrieve @Mapper annotation", usedMapper, mapperSettings.getAnnotationMirror()
                );
            }

            for ( TypeMirror mapper : mapperSettings.uses() ) {
                methods.addAll( retrieveMethods( asTypeElement( mapper ), mapperToImplement ) );
            }
        }

        return methods;
    }

    private TypeElement asTypeElement(TypeMirror usedMapper) {
        return (TypeElement) ( (DeclaredType) usedMapper ).asElement();
    }

    private List<Element> allEnclosingElementsIncludeSuper(TypeElement element) {
        List<Element> enclosedElements = new ArrayList<Element>( element.getEnclosedElements() );
        for ( TypeMirror interfaceType : element.getInterfaces() ) {
            enclosedElements.addAll( allEnclosingElementsIncludeSuper( asTypeElement( interfaceType ) ) );
        }

        if ( hasNonObjectSuperclass( element ) ) {
            enclosedElements.addAll( allEnclosingElementsIncludeSuper( asTypeElement( element.getSuperclass() ) ) );
        }

        return enclosedElements;
    }

    /**
     * @param element the type element to check
     * @return <code>true</code>, iff the type has a super-class that is not java.lang.Object
     */
    private boolean hasNonObjectSuperclass(TypeElement element) {
        return element.getSuperclass().getKind() == TypeKind.DECLARED
            && asTypeElement( element.getSuperclass() ).getSuperclass().getKind() == TypeKind.DECLARED;
    }

    private SourceMethod getMethod(TypeElement usedMapper,
                                   ExecutableElement method,
                                   TypeElement mapperToImplement) {
        List<Parameter> parameters = typeFactory.getParameters( method );
        Type returnType = typeFactory.getReturnType( method );
        List<Type> exceptionTypes = typeFactory.getThrownTypes( method );

        //add method with property mappings if an implementation needs to be generated
        boolean methodRequiresImplementation = method.getModifiers().contains( Modifier.ABSTRACT );
        boolean containsTargetTypeParameter = SourceMethod.containsTargetTypeParameter( parameters );

        if ( ( usedMapper.equals( mapperToImplement ) ) && methodRequiresImplementation ) {
            List<Parameter> sourceParameters = extractSourceParameters( parameters );
            Parameter targetParameter = extractTargetParameter( parameters );
            Type resultType = selectResultType( returnType, targetParameter );

            boolean isValid =
                checkParameterAndReturnType(
                    method,
                    sourceParameters,
                    targetParameter,
                    resultType,
                    returnType,
                    containsTargetTypeParameter );

            if ( isValid ) {
                return
                    SourceMethod.forMethodRequiringImplementation(
                        method,
                        parameters,
                        returnType,
                        exceptionTypes,
                        getMappings( method ),
                        IterableMapping.fromPrism( IterableMappingPrism.getInstanceOn( method ) ),
                        MapMapping.fromPrism( MapMappingPrism.getInstanceOn( method ) ),
                        typeUtils
                    );
            }
            else {
                return null;
            }
        }
        //otherwise add reference to existing mapper method
        else if ( isValidReferencedMethod( parameters ) || isValidFactoryMethod( parameters ) ) {
            Type usedMapperAsType = typeFactory.getType( usedMapper );
            Type mapperToImplementAsType = typeFactory.getType( mapperToImplement );
            if ( isAccessible( mapperToImplementAsType, usedMapperAsType, method ) ) {
                return SourceMethod.forReferencedMethod(
                        usedMapper.equals( mapperToImplement )  ? null : usedMapperAsType,
                        method,
                        parameters,
                        returnType,
                        exceptionTypes,
                        typeUtils
                );
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }

    private boolean isValidReferencedMethod(List<Parameter> parameters) {
        return isValidReferencedOrFactoryMethod( 1, parameters );
    }

    private boolean isValidFactoryMethod(List<Parameter> parameters) {
        return isValidReferencedOrFactoryMethod( 0, parameters );
    }

    private boolean isValidReferencedOrFactoryMethod(int sourceParamCount, List<Parameter> parameters) {
        int validSourceParameters = 0;
        int targetParameters = 0;
        int targetTypeParameters = 0;

        for ( Parameter param : parameters ) {
            if ( param.isMappingTarget() ) {
                targetParameters++;
            }

            if ( param.isTargetType() ) {
                targetTypeParameters++;
            }

            if ( !param.isMappingTarget() && !param.isTargetType() ) {
                validSourceParameters++;
            }
        }
        return validSourceParameters == sourceParamCount && targetParameters == 0 && targetTypeParameters <= 1
            && parameters.size() == validSourceParameters + targetParameters + targetTypeParameters;
    }


    private boolean isAccessible( Type mapperToImplement, Type usedMapper, ExecutableElement method ) {

        if ( method.getModifiers().contains( Modifier.PRIVATE ) ) {
            return false;
        }
        else if ( method.getModifiers().contains( Modifier.PROTECTED ) ) {
            return mapperToImplement.isAssignableTo( usedMapper ) ||
                    mapperToImplement.getPackageName().equals( usedMapper.getPackageName() );
        }
        else if ( !method.getModifiers().contains( Modifier.PUBLIC ) ) {
            // default
            return mapperToImplement.getPackageName().equals( usedMapper.getPackageName() );
        }
        // public
        return true;
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
                                                Parameter targetParameter, Type resultType, Type returnType,
                                                boolean containsTargetTypeParameter) {
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

        if ( containsTargetTypeParameter ) {
            messager.printMessage(
                Kind.ERROR,
                "Can't generate mapping method that has a parameter annotated with @TargetType.",
                method );
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

        if ( parameterType.isEnumType() && !resultType.isEnumType() ) {
            messager.printMessage(
                Kind.ERROR,
                "Can't generate mapping method from enum type to non-enum type.",
                method
            );
            return false;
        }

        if ( !parameterType.isEnumType() && resultType.isEnumType() ) {
            messager.printMessage(
                Kind.ERROR,
                "Can't generate mapping method from non-enum type to enum type.",
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
    private Map<String, List<Mapping>> getMappings(ExecutableElement method) {
        Map<String, List<Mapping>> mappings = new HashMap<String, List<Mapping>>();

        MappingPrism mappingAnnotation = MappingPrism.getInstanceOn( method );
        MappingsPrism mappingsAnnotation = MappingsPrism.getInstanceOn( method );

        if ( mappingAnnotation != null ) {
            if ( !mappings.containsKey( mappingAnnotation.source() ) ) {
                mappings.put( mappingAnnotation.source(), new ArrayList<Mapping>() );
            }
            mappings.get( mappingAnnotation.source() ).add( Mapping.fromMappingPrism( mappingAnnotation, method ) );
        }

        if ( mappingsAnnotation != null ) {
            mappings.putAll( Mapping.fromMappingsPrism( mappingsAnnotation, method ) );
        }

        return mappings;
    }
}
