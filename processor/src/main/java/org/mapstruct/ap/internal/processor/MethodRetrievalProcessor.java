/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.processor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.BeanMapping;
import org.mapstruct.ap.internal.model.source.IterableMapping;
import org.mapstruct.ap.internal.model.source.MapMapping;
import org.mapstruct.ap.internal.model.source.Mapping;
import org.mapstruct.ap.internal.model.source.ParameterProvidedMethods;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.ValueMapping;
import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import org.mapstruct.ap.internal.prism.IterableMappingPrism;
import org.mapstruct.ap.internal.prism.MapMappingPrism;
import org.mapstruct.ap.internal.prism.MappingPrism;
import org.mapstruct.ap.internal.prism.MappingsPrism;
import org.mapstruct.ap.internal.prism.ObjectFactoryPrism;
import org.mapstruct.ap.internal.prism.ValueMappingPrism;
import org.mapstruct.ap.internal.prism.ValueMappingsPrism;
import org.mapstruct.ap.internal.util.AccessorNamingUtils;
import org.mapstruct.ap.internal.util.AnnotationProcessingException;
import org.mapstruct.ap.internal.util.Executables;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.MapperConfiguration;
import org.mapstruct.ap.internal.util.Message;

import static org.mapstruct.ap.internal.util.Executables.getAllEnclosedExecutableElements;

/**
 * A {@link ModelElementProcessor} which retrieves a list of {@link SourceMethod}s
 * representing all the mapping methods of the given bean mapper type as well as
 * all referenced mapper methods declared by other mappers referenced by the
 * current mapper.
 *
 * @author Gunnar Morling
 */
public class MethodRetrievalProcessor implements ModelElementProcessor<Void, List<SourceMethod>> {

    private FormattingMessager messager;
    private TypeFactory typeFactory;
    private AccessorNamingUtils accessorNaming;
    private Types typeUtils;
    private Elements elementUtils;

    @Override
    public List<SourceMethod> process(ProcessorContext context, TypeElement mapperTypeElement, Void sourceModel) {
        this.messager = context.getMessager();
        this.typeFactory = context.getTypeFactory();
        this.accessorNaming = context.getAccessorNaming();
        this.typeUtils = context.getTypeUtils();
        this.elementUtils = context.getElementUtils();

        this.messager.note( 0, Message.PROCESSING_NOTE, mapperTypeElement );

        MapperConfiguration mapperConfig = MapperConfiguration.getInstanceOn( mapperTypeElement );

        if ( mapperConfig != null ) {
            this.messager.note( 0, Message.CONFIG_NOTE, mapperConfig.getClass().getName() );
        }

        if ( !mapperConfig.isValid() ) {
            throw new AnnotationProcessingException(
                "Couldn't retrieve @Mapper annotation",
                mapperTypeElement,
                mapperConfig.getAnnotationMirror() );
        }

        List<SourceMethod> prototypeMethods = retrievePrototypeMethods( mapperTypeElement, mapperConfig );
        return retrieveMethods( mapperTypeElement, mapperTypeElement, mapperConfig, prototypeMethods );
    }

    @Override
    public int getPriority() {
        return 1;
    }

    private List<SourceMethod> retrievePrototypeMethods(TypeElement mapperTypeElement,
            MapperConfiguration mapperConfig) {
        if ( mapperConfig.config() == null ) {
            return Collections.emptyList();
        }

        TypeElement typeElement = asTypeElement( mapperConfig.config() );
        List<SourceMethod> methods = new ArrayList<>();
        for ( ExecutableElement executable : getAllEnclosedExecutableElements( elementUtils, typeElement ) ) {

            ExecutableType methodType = typeFactory.getMethodType( mapperConfig.config(), executable );
            List<Parameter> parameters = typeFactory.getParameters( methodType, executable );
            boolean containsTargetTypeParameter = SourceMethod.containsTargetTypeParameter( parameters );

            // prototype methods don't have prototypes themselves
            List<SourceMethod> prototypeMethods = Collections.emptyList();

            SourceMethod method =
                getMethodRequiringImplementation(
                    methodType,
                    executable,
                    parameters,
                    containsTargetTypeParameter,
                    mapperConfig,
                    prototypeMethods,
                    mapperTypeElement
                );

            if ( method != null ) {
                methods.add( method );
            }
        }

        return methods;
    }

    /**
     * Retrieves the mapping methods declared by the given mapper type.
     *
     * @param usedMapper The type of interest (either the mapper to implement or a used mapper via @uses annotation)
     * @param mapperToImplement the top level type (mapper) that requires implementation
     * @param mapperConfig the mapper config
     * @param prototypeMethods prototype methods defined in mapper config type
     * @return All mapping methods declared by the given type
     */
    private List<SourceMethod> retrieveMethods(TypeElement usedMapper, TypeElement mapperToImplement,
                                               MapperConfiguration mapperConfig, List<SourceMethod> prototypeMethods) {
        List<SourceMethod> methods = new ArrayList<>();

        for ( ExecutableElement executable : getAllEnclosedExecutableElements( elementUtils, usedMapper ) ) {
            SourceMethod method = getMethod(
                usedMapper,
                executable,
                mapperToImplement,
                mapperConfig,
                prototypeMethods );

            if ( method != null ) {
                methods.add( method );
            }
        }

        //Add all methods of used mappers in order to reference them in the aggregated model
        if ( usedMapper.equals( mapperToImplement ) ) {
            for ( DeclaredType mapper : mapperConfig.uses() ) {
                methods.addAll( retrieveMethods(
                    asTypeElement( mapper ),
                    mapperToImplement,
                    mapperConfig,
                    prototypeMethods ) );
            }
        }

        return methods;
    }

    private TypeElement asTypeElement(DeclaredType type) {
        return (TypeElement) type.asElement();
    }

    private SourceMethod getMethod(TypeElement usedMapper,
                                   ExecutableElement method,
                                   TypeElement mapperToImplement,
                                   MapperConfiguration mapperConfig,
                                   List<SourceMethod> prototypeMethods) {

        ExecutableType methodType = typeFactory.getMethodType( (DeclaredType) usedMapper.asType(), method );
        List<Parameter> parameters = typeFactory.getParameters( methodType, method );
        Type returnType = typeFactory.getReturnType( methodType );

        boolean methodRequiresImplementation = method.getModifiers().contains( Modifier.ABSTRACT );
        boolean containsTargetTypeParameter = SourceMethod.containsTargetTypeParameter( parameters );

        //add method with property mappings if an implementation needs to be generated
        if ( ( usedMapper.equals( mapperToImplement ) ) && methodRequiresImplementation ) {
            return getMethodRequiringImplementation( methodType,
                method,
                parameters,
                containsTargetTypeParameter,
                mapperConfig,
                prototypeMethods,
                mapperToImplement );
        }
        // otherwise add reference to existing mapper method
        else if ( isValidReferencedMethod( parameters ) || isValidFactoryMethod( method, parameters, returnType )
            || isValidLifecycleCallbackMethod( method ) ) {
            return getReferencedMethod( usedMapper, methodType, method, mapperToImplement, parameters );
        }
        else {
            return null;
        }
    }

    private SourceMethod getMethodRequiringImplementation(ExecutableType methodType, ExecutableElement method,
            List<Parameter> parameters,
            boolean containsTargetTypeParameter,
            MapperConfiguration mapperConfig,
            List<SourceMethod> prototypeMethods,
            TypeElement mapperToImplement) {
        Type returnType = typeFactory.getReturnType( methodType );
        List<Type> exceptionTypes = typeFactory.getThrownTypes( methodType );
        List<Parameter> sourceParameters = Parameter.getSourceParameters( parameters );
        List<Parameter> contextParameters = Parameter.getContextParameters( parameters );
        Parameter targetParameter = extractTargetParameter( parameters );
        Type resultType = selectResultType( returnType, targetParameter );

        boolean isValid = checkParameterAndReturnType(
            method,
            sourceParameters,
            targetParameter,
            contextParameters,
            resultType,
            returnType,
            containsTargetTypeParameter
        );

        if ( !isValid ) {
            return null;
        }

        ParameterProvidedMethods contextProvidedMethods =
            retrieveContextProvidedMethods( contextParameters, mapperToImplement, mapperConfig );

        return new SourceMethod.Builder()
            .setExecutable( method )
            .setParameters( parameters )
            .setReturnType( returnType )
            .setExceptionTypes( exceptionTypes )
            .setMappings( getMappings( method ) )
            .setIterableMapping(
                IterableMapping.fromPrism(
                    IterableMappingPrism.getInstanceOn( method ),
                    method,
                    messager,
                    typeUtils
                ) )
            .setMapMapping(
                MapMapping.fromPrism( MapMappingPrism.getInstanceOn( method ), method, messager, typeUtils ) )
            .setBeanMapping(
                new BeanMapping.Builder()
                    .beanMappingPrism( BeanMappingPrism.getInstanceOn( method ) )
                    .messager( messager )
                    .method( method )
                    .typeUtils( typeUtils )
                    .typeFactory( typeFactory )
                    .build()
            )
            .setValueMappings( getValueMappings( method ) )
            .setTypeUtils( typeUtils )
            .setTypeFactory( typeFactory )
            .setMapperConfiguration( mapperConfig )
            .setPrototypeMethods( prototypeMethods )
            .setContextProvidedMethods( contextProvidedMethods )
            .build();
    }

    private ParameterProvidedMethods retrieveContextProvidedMethods(
            List<Parameter> contextParameters, TypeElement mapperToImplement, MapperConfiguration mapperConfig) {

        ParameterProvidedMethods.Builder builder = ParameterProvidedMethods.builder();
        for ( Parameter contextParam : contextParameters ) {
            if ( contextParam.getType().isPrimitive() || contextParam.getType().isArrayType() ) {
                continue;
            }
            List<SourceMethod> contextParamMethods = retrieveMethods(
                contextParam.getType().getTypeElement(),
                mapperToImplement,
                mapperConfig,
                Collections.emptyList() );

            List<SourceMethod> contextProvidedMethods = new ArrayList<>( contextParamMethods.size() );
            for ( SourceMethod sourceMethod : contextParamMethods ) {
                if ( sourceMethod.isLifecycleCallbackMethod() || sourceMethod.isObjectFactory() ) {
                    contextProvidedMethods.add( sourceMethod );
                }
            }

            builder.addMethodsForParameter( contextParam, contextProvidedMethods );
        }

        return builder.build();
    }

    private SourceMethod getReferencedMethod(TypeElement usedMapper, ExecutableType methodType,
                                             ExecutableElement method, TypeElement mapperToImplement,
                                             List<Parameter> parameters) {
        Type returnType = typeFactory.getReturnType( methodType );
        List<Type> exceptionTypes = typeFactory.getThrownTypes( methodType );
        Type usedMapperAsType = typeFactory.getType( usedMapper );
        Type mapperToImplementAsType = typeFactory.getType( mapperToImplement );

        if ( !mapperToImplementAsType.canAccess( usedMapperAsType, method ) ) {
            return null;
        }


        Type definingType = typeFactory.getType( method.getEnclosingElement().asType() );

        return new SourceMethod.Builder()
            .setDeclaringMapper( usedMapper.equals( mapperToImplement ) ? null : usedMapperAsType )
            .setDefininingType( definingType )
            .setExecutable( method )
            .setParameters( parameters )
            .setReturnType( returnType )
            .setExceptionTypes( exceptionTypes )
            .setTypeUtils( typeUtils )
            .setTypeFactory( typeFactory )
            .build();
    }

    private boolean isValidLifecycleCallbackMethod(ExecutableElement method) {
        return Executables.isLifecycleCallbackMethod( method );
    }

    private boolean isValidReferencedMethod(List<Parameter> parameters) {
        return isValidReferencedOrFactoryMethod( 1, 1, parameters );
    }

    private boolean isValidFactoryMethod(ExecutableElement method, List<Parameter> parameters, Type returnType) {
        return !isVoid( returnType )
            && ( isValidReferencedOrFactoryMethod( 0, 0, parameters ) || hasFactoryAnnotation( method ) );
    }

    private boolean hasFactoryAnnotation(ExecutableElement method) {
        return ObjectFactoryPrism.getInstanceOn( method ) != null;
    }

    private boolean isVoid(Type returnType) {
        return returnType.getTypeMirror().getKind() == TypeKind.VOID;
    }

    private boolean isValidReferencedOrFactoryMethod(int sourceParamCount, int targetParamCount,
                                                     List<Parameter> parameters) {
        int validSourceParameters = 0;
        int targetParameters = 0;
        int targetTypeParameters = 0;

        for ( Parameter param : parameters ) {
            if ( param.isMappingTarget() ) {
                targetParameters++;
            }
            else if ( param.isTargetType() ) {
                targetTypeParameters++;
            }
            else if ( !param.isMappingContext() ) {
                validSourceParameters++;
            }
        }

        return validSourceParameters == sourceParamCount
            && targetParameters <= targetParamCount
            && targetTypeParameters <= 1;
    }

    private Parameter extractTargetParameter(List<Parameter> parameters) {
        for ( Parameter param : parameters ) {
            if ( param.isMappingTarget() ) {
                return param;
            }
        }

        return null;
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
                                                Parameter targetParameter, List<Parameter> contextParameters,
                                                Type resultType, Type returnType, boolean containsTargetTypeParameter) {
        if ( sourceParameters.isEmpty() ) {
            messager.printMessage( method, Message.RETRIEVAL_NO_INPUT_ARGS );
            return false;
        }

        if ( targetParameter != null
            && ( sourceParameters.size() + contextParameters.size() + 1 != method.getParameters().size() ) ) {
            messager.printMessage( method, Message.RETRIEVAL_DUPLICATE_MAPPING_TARGETS );
            return false;
        }

        if ( isVoid( resultType ) ) {
            messager.printMessage( method, Message.RETRIEVAL_VOID_MAPPING_METHOD );
            return false;
        }

        if ( returnType.getTypeMirror().getKind() != TypeKind.VOID &&
                        !resultType.isAssignableTo( returnType ) &&
                        !resultType.isAssignableTo( typeFactory.effectiveResultTypeFor( returnType, null ) ) ) {
            messager.printMessage( method, Message.RETRIEVAL_NON_ASSIGNABLE_RESULTTYPE );
            return false;
        }

        for ( Parameter sourceParameter : sourceParameters ) {
            if ( sourceParameter.getType().isTypeVar() ) {
                messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_SOURCE );
                return false;
            }
        }

        Set<Type> contextParameterTypes = new HashSet<>();
        for ( Parameter contextParameter : contextParameters ) {
            if ( !contextParameterTypes.add( contextParameter.getType() ) ) {
                messager.printMessage( method, Message.RETRIEVAL_CONTEXT_PARAMS_WITH_SAME_TYPE );
                return false;
            }
        }

        if ( returnType.isTypeVar() || resultType.isTypeVar() ) {
                messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_RESULT );
                return false;
        }

        Type parameterType = sourceParameters.get( 0 ).getType();

        if ( parameterType.isIterableOrStreamType() && !resultType.isIterableOrStreamType() ) {
            messager.printMessage( method, Message.RETRIEVAL_ITERABLE_TO_NON_ITERABLE );
            return false;
        }

        if ( containsTargetTypeParameter ) {
            messager.printMessage( method, Message.RETRIEVAL_MAPPING_HAS_TARGET_TYPE_PARAMETER );
            return false;
        }

        if ( !parameterType.isIterableOrStreamType() && resultType.isIterableOrStreamType() ) {
            messager.printMessage( method, Message.RETRIEVAL_NON_ITERABLE_TO_ITERABLE );
            return false;
        }

        if ( parameterType.isPrimitive() ) {
            messager.printMessage( method, Message.RETRIEVAL_PRIMITIVE_PARAMETER );
            return false;
        }

        if ( resultType.isPrimitive() ) {
            messager.printMessage( method, Message.RETRIEVAL_PRIMITIVE_RETURN );
            return false;
        }

        if ( parameterType.isEnumType() && !( resultType.isEnumType() || resultType.isString() ) ) {
            messager.printMessage( method, Message.RETRIEVAL_ENUM_TO_NON_ENUM );
            return false;
        }

        if ( !( parameterType.isEnumType() || parameterType.isString() ) && resultType.isEnumType() ) {
            messager.printMessage( method, Message.RETRIEVAL_NON_ENUM_TO_ENUM );
            return false;
        }

        for ( Type typeParameter : resultType.getTypeParameters() ) {
            if ( typeParameter.isTypeVar() ) {
                messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_RESULT );
                return false;
            }
            if ( typeParameter.isWildCardExtendsBound() ) {
                messager.printMessage( method, Message.RETRIEVAL_WILDCARD_EXTENDS_BOUND_RESULT );
                return false;
            }
        }

        for ( Type typeParameter : parameterType.getTypeParameters() ) {
            if ( typeParameter.isWildCardSuperBound() ) {
                messager.printMessage( method, Message.RETRIEVAL_WILDCARD_SUPER_BOUND_SOURCE );
                return false;
            }

            if ( typeParameter.isTypeVar() ) {
                messager.printMessage( method, Message.RETRIEVAL_TYPE_VAR_SOURCE );
                return false;
            }
        }

        return true;
    }

    /**
     * Retrieves the mappings configured via {@code @Mapping} from the given
     * method.
     *
     * @param method The method of interest
     *
     * @return The mappings for the given method, keyed by target property name
     */
    private Set<Mapping> getMappings(ExecutableElement method) {
        Set<Mapping> mappings = new LinkedHashSet<>(  );

        MappingPrism mappingAnnotation = MappingPrism.getInstanceOn( method );
        MappingsPrism mappingsAnnotation = MappingsPrism.getInstanceOn( method );

        if ( mappingAnnotation != null ) {
            mappings.add( Mapping.fromMappingPrism( mappingAnnotation, method, messager, typeUtils ) );
        }

        if ( mappingsAnnotation != null ) {
            mappings.addAll( Mapping.fromMappingsPrism( mappingsAnnotation, method, messager, typeUtils ) );
        }

        return mappings;
    }

    /**
     * Retrieves the mappings configured via {@code @ValueMapping} from the given
     * method.
     *
     * @param method The method of interest
     *
     * @return The mappings for the given method, keyed by target property name
     */
    private List<ValueMapping> getValueMappings(ExecutableElement method) {
        List<ValueMapping> valueMappings = new ArrayList<>();

        ValueMappingPrism mappingAnnotation = ValueMappingPrism.getInstanceOn( method );
        ValueMappingsPrism mappingsAnnotation = ValueMappingsPrism.getInstanceOn( method );

        if ( mappingAnnotation != null ) {
            ValueMapping valueMapping = ValueMapping.fromMappingPrism( mappingAnnotation );
            if ( valueMapping != null ) {
                valueMappings.add( valueMapping );
            }
        }

        if ( mappingsAnnotation != null ) {
            ValueMapping.fromMappingsPrism( mappingsAnnotation, method, messager, valueMappings );
        }

        return valueMappings;
    }
}
