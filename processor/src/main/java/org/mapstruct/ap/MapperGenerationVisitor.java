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
package org.mapstruct.ap;

import java.beans.Introspector;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementKindVisitor6;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.JavaFileObject;

import org.mapstruct.ap.conversion.Conversion;
import org.mapstruct.ap.conversion.Conversions;
import org.mapstruct.ap.model.IterableMappingMethod;
import org.mapstruct.ap.model.Mapper;
import org.mapstruct.ap.model.MappingMethod;
import org.mapstruct.ap.model.MappingMethodReference;
import org.mapstruct.ap.model.Options;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.ReportingPolicy;
import org.mapstruct.ap.model.SimpleMappingMethod;
import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.Parameter;
import org.mapstruct.ap.util.Executables;
import org.mapstruct.ap.util.Filters;
import org.mapstruct.ap.util.Strings;
import org.mapstruct.ap.util.TypeUtil;
import org.mapstruct.ap.writer.ModelWriter;

import static javax.lang.model.util.ElementFilter.methodsIn;

/**
 * An {@link ElementVisitor} which generates the implementations for mapper
 * interfaces (interfaces annotated with {@code @Mapper}.
 * </p>
 * Implementation notes:
 * </p>
 * The mapper generation happens by building up a model representation of
 * the mapper to be generated (a {@link Mapper} object), which is then written
 * into a file using the FreeMarker template engine.
 * </p>
 * The model instantiation happens in two phases/passes: The first one retrieves
 * the mapping methods of the given interfaces and their configuration (the
 * <i>source</i> model). In the second pass the individual methods are
 * aggregated into the <i>target</i> model, which contains a {@link BeanMapping}
 * each pair of source and target type which has references to forward and
 * reverse mapping methods as well as the methods for mapping the element types
 * (if it is a collection mapping) and {@link Conversion}s if applicable.
 * </p>
 * For reading annotation attributes, prisms as generated with help of the <a
 * href="https://java.net/projects/hickory">Hickory</a> tool are used. These
 * prisms allow a comfortable access to annotations and their attributes without
 * depending on their class objects.
 *
 * @author Gunnar Morling
 */
public class MapperGenerationVisitor extends ElementKindVisitor6<Void, Void> {

    private static final String IMPLEMENTATION_SUFFIX = "Impl";

    private final ProcessingEnvironment processingEnvironment;
    private final Types typeUtils;
    private final Elements elementUtils;
    private final TypeUtil typeUtil;
    private final Conversions conversions;
    private final Options options;

    private boolean mappingErroneous = false;

    public MapperGenerationVisitor(ProcessingEnvironment processingEnvironment, Options options) {
        this.processingEnvironment = processingEnvironment;
        this.typeUtils = processingEnvironment.getTypeUtils();
        this.elementUtils = processingEnvironment.getElementUtils();
        this.typeUtil = new TypeUtil( elementUtils, typeUtils );
        this.conversions = new Conversions( elementUtils, typeUtils, typeUtil );
        this.options = options;
    }

    @Override
    public Void visitTypeAsInterface(TypeElement element, Void p) {
        Mapper model = retrieveModel( element );

        if ( !mappingErroneous ) {
            String sourceFileName = element.getQualifiedName() + IMPLEMENTATION_SUFFIX;
            writeModelToSourceFile( sourceFileName, model );
        }

        return null;
    }

    private void writeModelToSourceFile(String fileName, Mapper model) {
        JavaFileObject sourceFile;
        try {
            sourceFile = processingEnvironment.getFiler().createSourceFile( fileName );
        }
        catch ( IOException e ) {
            throw new RuntimeException( e );
        }

        new ModelWriter().writeModel( sourceFile, model );
    }

    private Mapper retrieveModel(TypeElement element) {
        //1.) build up "source" model
        List<Method> methods = retrieveMethods( element, true );

        //2.) build up aggregated "target" model
        List<MappingMethod> mappings = getMappingMethods(
            methods,
            getEffectiveUnmappedTargetPolicy( element )
        );
        List<Type> usedMapperTypes = getUsedMapperTypes( element );

        Mapper mapper = new Mapper(
            elementUtils.getPackageOf( element ).getQualifiedName().toString(),
            element.getSimpleName().toString(),
            element.getSimpleName() + IMPLEMENTATION_SUFFIX,
            mappings,
            usedMapperTypes,
            options
        );

        return mapper;
    }

    /**
     * Returns the effective policy for reporting unmapped target properties. If
     * explicitly set via {@code Mapper}, this value will be returned. Otherwise
     * the value from the corresponding processor option will be returned. If
     * that is not set either, the default value from
     * {@code Mapper#unmappedTargetPolicy()} will be returned.
     *
     * @param element The type declaring the generated mapper type
     *
     * @return The effective policy for reporting unmapped target properties.
     */
    private ReportingPolicy getEffectiveUnmappedTargetPolicy(TypeElement element) {
        MapperPrism mapperPrism = MapperPrism.getInstanceOn( element );
        boolean setViaAnnotation = mapperPrism.values.unmappedTargetPolicy() != null;
        ReportingPolicy annotationValue = ReportingPolicy.valueOf( mapperPrism.unmappedTargetPolicy() );

        if ( setViaAnnotation ||
            options.getUnmappedTargetPolicy() == null ) {
            return annotationValue;
        }
        else {
            return options.getUnmappedTargetPolicy();
        }
    }

    private List<MappingMethod> getMappingMethods(List<Method> methods, ReportingPolicy unmappedTargetPolicy) {
        List<MappingMethod> mappingMethods = new ArrayList<MappingMethod>();

        for ( Method method : methods ) {
            if ( method.getDeclaringMapper() != null ) {
                continue;
            }

            if ( method.getMappings().isEmpty() ) {
                Method reverseMappingMethod = getReverseMappingMethod( methods, method );
                if ( reverseMappingMethod != null && !reverseMappingMethod.getMappings().isEmpty() ) {
                    method.setMappings( reverse( reverseMappingMethod.getMappings() ) );
                }
            }

            if ( method.isIterableMapping() ) {
                mappingMethods.add( getIterableMappingMethod( methods, method ) );
            }
            else {
                mappingMethods.add( getSimpleMappingMethod( methods, method, unmappedTargetPolicy ) );
            }
        }
        return mappingMethods;
    }

    private Map<String, Mapping> reverse(Map<String, Mapping> mappings) {
        Map<String, Mapping> reversed = new HashMap<String, Mapping>();

        for ( Mapping mapping : mappings.values() ) {
            reversed.put( mapping.getTargetName(), mapping.reverse() );
        }
        return reversed;
    }

    private MappingMethod getSimpleMappingMethod(List<Method> methods, Method method,
                                                 ReportingPolicy unmappedTargetPolicy) {
        List<PropertyMapping> propertyMappings = new ArrayList<PropertyMapping>();
        Set<String> mappedTargetProperties = new HashSet<String>();

        Map<String, Mapping> mappings = method.getMappings();

        TypeElement returnTypeElement = (TypeElement) typeUtils.asElement( method.getExecutable().getReturnType() );
        TypeElement parameterElement = (TypeElement) typeUtils.asElement(
            method.getExecutable()
                .getParameters()
                .get( 0 )
                .asType()
        );

        List<ExecutableElement> sourceGetters = Filters.getterMethodsIn(
            elementUtils.getAllMembers( parameterElement )
        );
        List<ExecutableElement> targetSetters = Filters.setterMethodsIn(
            elementUtils.getAllMembers( returnTypeElement )
        );

        reportErrorIfMappedPropertiesDontExist( method );

        for ( ExecutableElement getterMethod : sourceGetters ) {
            String sourcePropertyName = Executables.getPropertyName( getterMethod );
            Mapping mapping = mappings.get( sourcePropertyName );

            for ( ExecutableElement setterMethod : targetSetters ) {
                String targetPropertyName = Executables.getPropertyName( setterMethod );

                if ( targetPropertyName.equals( mapping != null ? mapping.getTargetName() : sourcePropertyName ) ) {
                    PropertyMapping property = getPropertyMapping(
                        methods,
                        method,
                        getterMethod,
                        setterMethod
                    );

                    propertyMappings.add( property );

                    mappedTargetProperties.add( targetPropertyName );
                }
            }
        }

        reportErrorForUnmappedTargetPropertiesIfRequired(
            method,
            unmappedTargetPolicy,
            mappedTargetProperties
        );

        return new SimpleMappingMethod(
            method.getName(),
            method.getParameterName(),
            method.getSourceType(),
            method.getTargetType(),
            propertyMappings
        );
    }

    private PropertyMapping getPropertyMapping(List<Method> methods, Method method, ExecutableElement getterMethod,
                                               ExecutableElement setterMethod) {
        Type sourceType = retrieveReturnType( getterMethod );
        Type targetType = retrieveParameter( setterMethod ).getType();

        MappingMethodReference propertyMappingMethod = getMappingMethodReference( methods, sourceType, targetType );
        Conversion conversion = conversions.getConversion(
            sourceType,
            targetType
        );

        PropertyMapping property = new PropertyMapping(
            method.getParameterName(),
            Introspector.decapitalize( method.getTargetType().getName() ),
            Executables.getPropertyName( getterMethod ),
            getterMethod.getSimpleName().toString(),
            sourceType,
            Executables.getPropertyName( setterMethod ),
            setterMethod.getSimpleName().toString(),
            targetType,
            propertyMappingMethod,
            conversion != null ? conversion.to(
                method.getParameterName() + "." + getterMethod.getSimpleName().toString() + "()",
                targetType
            ) : null
        );

        reportErrorIfPropertyCanNotBeMapped(
            method,
            property
        );

        return property;
    }

    private MappingMethod getIterableMappingMethod(List<Method> methods, Method method) {
        String toConversionString = getIterableConversionString(
            conversions,
            method.getSourceType().getElementType(),
            method.getTargetType().getElementType(),
            true
        );

        return new IterableMappingMethod(
            method.getName(),
            method.getParameterName(),
            method.getSourceType(),
            method.getTargetType(),
            getMappingMethodReference(
                methods,
                method.getSourceType().getElementType(),
                method.getTargetType().getElementType()
            ),
            toConversionString
        );
    }

    private void reportErrorForUnmappedTargetPropertiesIfRequired(Method method,
                                                                  ReportingPolicy unmappedTargetPolicy,
                                                                  Set<String> mappedTargetProperties) {

        if ( method.getTargetProeprties().size() > mappedTargetProperties.size() &&
            unmappedTargetPolicy.requiresReport() ) {
            method.getTargetProeprties().removeAll( mappedTargetProperties );
            printMessage(
                unmappedTargetPolicy,
                MessageFormat.format(
                    "Unmapped target {0,choice,1#property|1<properties}: \"{1}\"",
                    method.getTargetProeprties().size(),
                    Strings.join( method.getTargetProeprties(), ", " )
                ),
                method.getExecutable()
            );
        }
    }

    private void reportErrorIfPropertyCanNotBeMapped(Method method, PropertyMapping property) {
        if ( property.getSourceType().equals( property.getTargetType() ) ) {
            return;
        }

        //no mapping method nor conversion nor collection with default implementation
        if ( !(
            property.getMappingMethod() != null ||
                property.getConversion() != null ||
                ( property.getTargetType().isCollectionType() && property.getTargetType()
                    .getCollectionImplementationType() != null ) ) ) {

            printMessage(
                ReportingPolicy.ERROR,
                String.format(
                    "Can't map property \"%s %s\" to \"%s %s\".",
                    property.getSourceType(),
                    property.getSourceName(),
                    property.getTargetType(),
                    property.getTargetName()
                ),
                method.getExecutable()
            );
        }
    }

    private String getIterableConversionString(Conversions conversions, Type sourceElementType, Type targetElementType,
                                               boolean isToConversion) {
        Conversion conversion = conversions.getConversion( sourceElementType, targetElementType );

        if ( conversion == null ) {
            return null;
        }

        return conversion.to(
            Introspector.decapitalize( sourceElementType.getName() ),
            targetElementType
        );
    }

    private List<Type> getUsedMapperTypes(TypeElement element) {
        List<Type> usedMapperTypes = new LinkedList<Type>();
        MapperPrism mapperPrism = MapperPrism.getInstanceOn( element );
        for ( TypeMirror usedMapper : mapperPrism.uses() ) {
            usedMapperTypes.add( typeUtil.retrieveType( usedMapper ) );
        }
        return usedMapperTypes;
    }

    private MappingMethodReference getMappingMethodReference(Iterable<Method> methods, Type parameterType,
                                                             Type returnType) {
        for ( Method oneMethod : methods ) {
            if ( oneMethod.getSourceType().equals( parameterType ) && oneMethod.getTargetType().equals( returnType ) ) {
                return new MappingMethodReference(
                    oneMethod.getDeclaringMapper(),
                    oneMethod.getName(),
                    oneMethod.getParameterName(),
                    oneMethod.getSourceType(),
                    oneMethod.getTargetType()
                );
            }
        }

        return null;
    }

    private Method getReverseMappingMethod(List<Method> rawMethods, Method method) {
        for ( Method oneMethod : rawMethods ) {
            if ( oneMethod.reverses( method ) ) {
                return oneMethod;
            }
        }
        return null;
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

        //TODO Extract to separate method
        for ( ExecutableElement method : methodsIn( element.getEnclosedElements() ) ) {
            Parameter parameter = retrieveParameter( method );
            Type returnType = retrieveReturnType( method );
            Element returnTypeElement = typeUtils.asElement( method.getReturnType() );
            Element parameterElement = typeUtils.asElement( method.getParameters().get( 0 ).asType() );

            boolean mappingErroneous = false;

            if ( implementationRequired ) {
                if ( parameter.getType().isIterableType() && !returnType.isIterableType() ) {
                    printMessage(
                        ReportingPolicy.ERROR,
                        "Can't generate mapping method from iterable type to non-iterable type.",
                        method
                    );
                    mappingErroneous = true;
                }
                if ( !parameter.getType().isIterableType() && returnType.isIterableType() ) {
                    printMessage(
                        ReportingPolicy.ERROR,
                        "Can't generate mapping method from non-iterable type to iterable type.",
                        method
                    );
                    mappingErroneous = true;
                }
                if ( parameter.getType().isPrimitive() ) {
                    printMessage(
                        ReportingPolicy.ERROR,
                        "Can't generate mapping method with primitive parameter type.",
                        method
                    );
                    mappingErroneous = true;
                }
                if ( returnType.isPrimitive() ) {
                    printMessage(
                        ReportingPolicy.ERROR,
                        "Can't generate mapping method with primitive return type.",
                        method
                    );
                    mappingErroneous = true;
                }

                if ( mappingErroneous ) {
                    continue;
                }
            }

            //add method with property mappings if an implementation needs to be generated
            if ( implementationRequired ) {
                Set<String> sourceProperties = Executables.getPropertyNames(
                    Filters.getterMethodsIn( parameterElement.getEnclosedElements() )
                );
                Set<String> targetProperties = Executables.getPropertyNames(
                    Filters.setterMethodsIn( returnTypeElement.getEnclosedElements() )
                );

                methods.add(
                    Method.forMethodRequiringImplementation(
                        method,
                        parameter.getName(),
                        parameter.getType(),
                        returnType,
                        sourceProperties,
                        targetProperties,
                        getMappings( method )
                    )
                );
            }
            //otherwise add reference to existing mapper method
            else {
                methods.add(
                    Method.forReferencedMethod(
                        typeUtil.getType( typeUtils.getDeclaredType( element ) ),
                        method,
                        parameter.getName(),
                        parameter.getType(),
                        returnType
                    )
                );
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

    private void reportErrorIfMappedPropertiesDontExist(Method method) {
        for ( Mapping mappedProperty : method.getMappings().values() ) {
            if ( !method.getSourceProperties().contains( mappedProperty.getSourceName() ) ) {
                printMessage(
                    ReportingPolicy.ERROR,
                    String.format(
                        "Unknown property \"%s\" in parameter type %s.",
                        mappedProperty.getSourceName(),
                        method.getSourceType()
                    ), method.getExecutable(), mappedProperty.getMirror(), mappedProperty.getSourceAnnotationValue()
                );
            }
            if ( !method.getTargetProeprties().contains( mappedProperty.getTargetName() ) ) {
                printMessage(
                    ReportingPolicy.ERROR,
                    String.format(
                        "Unknown property \"%s\" in return type %s.",
                        mappedProperty.getTargetName(),
                        method.getTargetType()
                    ), method.getExecutable(), mappedProperty.getMirror(), mappedProperty.getTargetAnnotationValue()
                );
            }
        }
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

    private Parameter retrieveParameter(ExecutableElement method) {
        List<? extends VariableElement> parameters = method.getParameters();

        if ( parameters.size() != 1 ) {
            //TODO: Log error
            return null;
        }

        VariableElement parameter = parameters.get( 0 );

        return new Parameter(
            parameter.getSimpleName().toString(),
            typeUtil.retrieveType( parameter.asType() )
        );
    }

    private Type retrieveReturnType(ExecutableElement method) {
        return typeUtil.retrieveType( method.getReturnType() );
    }

    private void printMessage(ReportingPolicy reportingPolicy, String message, Element element) {
        processingEnvironment.getMessager().printMessage( reportingPolicy.getDiagnosticKind(), message, element );
        if ( reportingPolicy.failsBuild() ) {
            mappingErroneous = true;
        }
    }

    private void printMessage(ReportingPolicy reportingPolicy, String message, Element element,
                              AnnotationMirror annotationMirror, AnnotationValue annotationValue) {
        processingEnvironment.getMessager()
            .printMessage(
                reportingPolicy.getDiagnosticKind(),
                message,
                element,
                annotationMirror,
                annotationValue
            );
        if ( reportingPolicy.failsBuild() ) {
            mappingErroneous = true;
        }
    }
}
