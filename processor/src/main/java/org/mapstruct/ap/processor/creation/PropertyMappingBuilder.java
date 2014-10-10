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
package org.mapstruct.ap.processor.creation;

import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.mapstruct.ap.model.Assignment;
import static org.mapstruct.ap.model.Assignment.AssignmentType.DIRECT;
import static org.mapstruct.ap.model.Assignment.AssignmentType.TYPE_CONVERTED;
import static org.mapstruct.ap.model.Assignment.AssignmentType.TYPE_CONVERTED_MAPPED;
import org.mapstruct.ap.model.IterableMappingMethod;
import org.mapstruct.ap.model.MapMappingMethod;
import org.mapstruct.ap.model.MapperReference;
import org.mapstruct.ap.model.PropertyMapping;
import org.mapstruct.ap.model.assignment.AdderWrapper;
import org.mapstruct.ap.model.assignment.AssignmentFactory;
import org.mapstruct.ap.model.assignment.GetterCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.NewCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.NullCheckWrapper;
import org.mapstruct.ap.model.assignment.SetterCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.SetterWrapper;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.ForgedMethod;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.util.Executables;

/**
 * This class is responsible for building property mapping methods.
 *
 * @author Sjaak Derksen
 */
public class PropertyMappingBuilder extends MappingBuilder {

    private enum TargetAccessorType {

        GETTER,
        SETTER,
        ADDER
    }

    public PropertyMappingBuilder( MappingContext ctx ) {
        super( ctx );
    }

    public PropertyMapping buildPropertyMappingForParameter(
            SourceMethod method,
            ExecutableElement targetAccessor,
            String targetPropertyName,
            Parameter parameter ) {

        // check if there's a mapping defined
        Mapping mapping = method.getMappingByTargetPropertyName( targetPropertyName );
        String dateFormat = null;
        List<TypeMirror> qualifiers = null;
        String sourcePropertyName;
        if ( mapping != null ) {
            dateFormat = mapping.getDateFormat();
            qualifiers = mapping.getQualifiers();
            sourcePropertyName = mapping.getSourcePropertyName();
        }
        else {
            sourcePropertyName = targetPropertyName;
        }

        List<ExecutableElement> sourceGetters = parameter.getType().getGetters();

        // then iterate over source accessors (assuming the source is a bean)
        for ( ExecutableElement sourceAccessor : sourceGetters ) {

            List<Mapping> sourceMappings = method.getMappings().get( sourcePropertyName );
            if ( method.getMappings().containsKey( sourcePropertyName ) ) {
                for ( Mapping sourceMapping : sourceMappings ) {
                    boolean mapsToOtherTarget = !sourceMapping.getTargetName().equals( targetPropertyName );
                    if ( Executables.getPropertyName( sourceAccessor ).equals( sourcePropertyName )
                            && !mapsToOtherTarget ) {
                        return getPropertyMapping(
                                getMapperReferences(),
                                getSourceModel(),
                                method,
                                parameter,
                                sourceAccessor,
                                targetAccessor,
                                targetPropertyName,
                                dateFormat,
                                qualifiers
                        );
                    }
                }
            }
            else if ( Executables.getPropertyName( sourceAccessor ).equals( sourcePropertyName ) ) {
                return getPropertyMapping(
                        getMapperReferences(),
                        getSourceModel(),
                        method,
                        parameter,
                        sourceAccessor,
                        targetAccessor,
                        targetPropertyName,
                        dateFormat,
                        qualifiers
                );
            }
        }
        return null;
    }

    /**
     * Creates a {@link PropertyMapping} representing the mapping of the given constant expression into the target
     * property.
     * @param method
     * @param constantExpression
     * @param targetAccessor
     * @param dateFormat
     * @param qualifiers
     * @return
     */
    public PropertyMapping buildConstantMapping( SourceMethod method,
            String constantExpression,
            ExecutableElement targetAccessor,
            String dateFormat,
            List<TypeMirror> qualifiers ) {

        // source
        String mappedElement = "constant '" + constantExpression + "'";
        Type sourceType = getTypeFactory().getType( String.class );

        // target
        Type targetType;
        if ( Executables.isSetterMethod( targetAccessor ) ) {
            targetType = getTypeFactory().getSingleParameter( targetAccessor ).getType();
        }
        else {
            targetType = getTypeFactory().getReturnType( targetAccessor );
        }
        String targetPropertyName = Executables.getPropertyName( targetAccessor );

        Assignment assignment = getMappingResolver().getTargetAssignment( method,
                mappedElement,
                sourceType,
                targetType,
                targetPropertyName,
                dateFormat,
                qualifiers,
                constantExpression
        );

        if ( assignment != null ) {

            // target accessor is setter, so decorate assignment as setter
            assignment = new SetterWrapper( assignment, method.getThrownTypes() );

            // wrap when dealing with getter only on target
            if ( Executables.isGetterMethod( targetAccessor ) ) {
                assignment = new GetterCollectionOrMapWrapper( assignment );
            }
        }
        else {
            getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                            "Can't map \"%s %s\" to \"%s %s\".",
                            sourceType,
                            constantExpression,
                            targetType,
                            targetPropertyName
                    ),
                    method.getExecutable()
            );
        }

        return new PropertyMapping( targetAccessor.getSimpleName().toString(), targetType, assignment );
    }

    /**
     * Creates a {@link PropertyMapping} representing the mapping of the given java expression into the target property.
     *
     * @param method
     * @param javaExpression
     * @param targetAccessor
     * @return
     */
    public PropertyMapping buildJavaExpressionMapping( SourceMethod method,
            String javaExpression,
            ExecutableElement targetAccessor ) {

        Assignment assignment = AssignmentFactory.createSimple( javaExpression );
        assignment = new SetterWrapper( assignment, method.getThrownTypes() );

        Type targetType;
        if ( Executables.isSetterMethod( targetAccessor ) ) {
            targetType = getTypeFactory().getSingleParameter( targetAccessor ).getType();
        }
        else {
            targetType = getTypeFactory().getReturnType( targetAccessor );

            // target accessor is getter, so wrap the setter in getter map/ collection handling
            assignment = new GetterCollectionOrMapWrapper( assignment );
        }

        return new PropertyMapping( targetAccessor.getSimpleName().toString(), targetType, assignment );
    }

    private PropertyMapping getPropertyMapping( List<MapperReference> mapperReferences,
            List<SourceMethod> methods,
            SourceMethod method,
            Parameter parameter,
            ExecutableElement sourceAccessor,
            ExecutableElement targetAccessor,
            String targetPropertyName,
            String dateFormat,
            List<TypeMirror> qualifiers ) {

        Type sourceType;
        Type targetType;
        TargetAccessorType targetAccessorType;
        String sourceReference = parameter.getName() + "." + sourceAccessor.getSimpleName().toString() + "()";
        String iteratorReference = null;
        boolean sourceIsCollection = false;
        if ( Executables.isSetterMethod( targetAccessor ) ) {
            sourceType = getTypeFactory().getReturnType( sourceAccessor );
            targetType = getTypeFactory().getSingleParameter( targetAccessor ).getType();
            targetAccessorType = TargetAccessorType.SETTER;
        }
        else if ( Executables.isAdderMethod( targetAccessor ) ) {
            sourceType = getTypeFactory().getReturnType( sourceAccessor );
            if ( sourceType.isCollectionType() ) {
                sourceIsCollection = true;
                sourceType = sourceType.getTypeParameters().get( 0 );
                iteratorReference = Executables.getElementNameForAdder( targetAccessor );
            }
            targetType = getTypeFactory().getSingleParameter( targetAccessor ).getType();
            targetAccessorType = TargetAccessorType.ADDER;
        }
        else {
            sourceType = getTypeFactory().getReturnType( sourceAccessor );
            targetType = getTypeFactory().getReturnType( targetAccessor );
            targetAccessorType = TargetAccessorType.GETTER;
        }
        String sourcePropertyName = Executables.getPropertyName( sourceAccessor );
        String mappedElement = "property '" + sourcePropertyName + "'";

        Assignment assignment = getMappingResolver().getTargetAssignment( method,
                mappedElement,
                sourceType,
                targetType,
                targetPropertyName,
                dateFormat,
                qualifiers,
                iteratorReference != null ? iteratorReference : sourceReference
        );

        if ( assignment == null ) {
            assignment = forgeMapping(
                    mapperReferences,
                    methods,
                    sourceType,
                    targetType,
                    sourceReference,
                    method.getExecutable()
            );
        }

        if ( assignment != null ) {

            if ( targetType.isCollectionOrMapType() ) {

                // wrap the setter in the collection / map initializers
                if ( targetAccessorType == TargetAccessorType.SETTER ) {

                    // wrap the assignment in a new Map or Collection implementation if this is not done in a mapping
                    // method. Note, typeconversons do not apply to collections or maps
                    Assignment newCollectionOrMap = null;
                    if ( assignment.getType() == DIRECT ) {
                        newCollectionOrMap = new NewCollectionOrMapWrapper( assignment, targetType.getImportTypes() );
                        newCollectionOrMap = new SetterWrapper( newCollectionOrMap, method.getThrownTypes() );
                    }

                    // wrap the assignment in the setter method
                    assignment = new SetterWrapper( assignment, method.getThrownTypes() );

                    // target accessor is setter, so wrap the setter in setter map/ collection handling
                    assignment = new SetterCollectionOrMapWrapper(
                            assignment,
                            targetAccessor.getSimpleName().toString(),
                            newCollectionOrMap
                    );
                }
                else {
                    // wrap the assignment in the setter method
                    assignment = new SetterWrapper( assignment, method.getThrownTypes() );

                    // target accessor is getter, so wrap the setter in getter map/ collection handling
                    assignment = new GetterCollectionOrMapWrapper( assignment );
                }

                // For collections and maps include a null check, when the assignment type is DIRECT.
                // for mapping methods (builtin / custom), the mapping method is responsible for the
                // null check. Typeconversions do not apply to collections and maps.
                if ( assignment.getType() == DIRECT ) {
                    assignment = new NullCheckWrapper( assignment );
                }
            }
            else {
                if ( targetAccessorType == TargetAccessorType.SETTER ) {
                    assignment = new SetterWrapper( assignment, method.getThrownTypes() );
                    if ( !sourceType.isPrimitive()
                            && ( assignment.getType() == TYPE_CONVERTED
                            || assignment.getType() == TYPE_CONVERTED_MAPPED
                            || assignment.getType() == DIRECT && targetType.isPrimitive() ) ) {
                        // for primitive types null check is not possible at all, but a conversion needs
                        // a null check.
                        assignment = new NullCheckWrapper( assignment );
                    }
                }
                else {
                    // TargetAccessorType must be ADDER
                    if ( sourceIsCollection ) {
                        assignment
                                = new AdderWrapper( assignment, method.getThrownTypes(), sourceReference, sourceType );
                    }
                    else {
                        // Possibly adding null to a target collection. So should be surrounded by an null check.
                        assignment = new SetterWrapper( assignment, method.getThrownTypes() );
                        assignment = new NullCheckWrapper( assignment );
                    }
                }
            }
        }
        else {
            getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                            "Can't map property \"%s %s\" to \"%s %s\".",
                            sourceType,
                            sourcePropertyName,
                            targetType,
                            targetPropertyName
                    ),
                    method.getExecutable()
            );
        }
        return new PropertyMapping(
                parameter.getName(),
                targetAccessor.getSimpleName().toString(),
                targetType,
                assignment
        );
    }

    public Assignment forgeMapping( List<MapperReference> mapperReferences,
            List<SourceMethod> methods,
            Type sourceType,
            Type targetType,
            String sourceReference,
            Element element ) {
        Assignment assignment = null;
        if ( sourceType.isCollectionType() && targetType.isCollectionType() ) {

            ForgedMethod methodToGenerate
                    = new ForgedMethod( sourceType, targetType, element );
            IterableMappingBuilder mappingBuilder = new IterableMappingBuilder( getMappingContext() );
            IterableMappingMethod iterableMappingMethod
                    = mappingBuilder.build(  methodToGenerate, null, null );
            if ( !getMappingsToGenerate().contains( iterableMappingMethod ) ) {
                getMappingsToGenerate().add( iterableMappingMethod );
            }
            assignment = AssignmentFactory.createMethodReference( methodToGenerate, null, targetType );
            assignment.setAssignment( AssignmentFactory.createSimple( sourceReference ) );

        }
        else if ( sourceType.isMapType() && targetType.isMapType() ) {

            ForgedMethod methodToGenerate
                    = new ForgedMethod( sourceType, targetType, element );
            MapMappingBuilder mapMappingBuilder = new MapMappingBuilder( getMappingContext() );
            MapMappingMethod mapMappingMethod
                    = mapMappingBuilder.build( methodToGenerate, null, null, null, null );
            if ( !getMappingsToGenerate().contains( mapMappingMethod ) ) {
                getMappingsToGenerate().add( mapMappingMethod );
            }
            assignment = AssignmentFactory.createMethodReference( methodToGenerate, null, targetType );
            assignment.setAssignment( AssignmentFactory.createSimple( sourceReference ) );
        }
        return assignment;
    }
}
