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
package org.mapstruct.ap.model;

import static org.mapstruct.ap.model.assignment.Assignment.AssignmentType.DIRECT;
import static org.mapstruct.ap.model.assignment.Assignment.AssignmentType.TYPE_CONVERTED;
import static org.mapstruct.ap.model.assignment.Assignment.AssignmentType.TYPE_CONVERTED_MAPPED;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

import org.mapstruct.ap.model.assignment.AdderWrapper;
import org.mapstruct.ap.model.assignment.Assignment;
import org.mapstruct.ap.model.assignment.GetterCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.NewCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.NullCheckWrapper;
import org.mapstruct.ap.model.assignment.SetterCollectionOrMapWrapper;
import org.mapstruct.ap.model.assignment.SetterWrapper;
import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.common.Type;
import org.mapstruct.ap.model.source.ForgedMethod;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.util.Executables;


/**
 * Represents the mapping between a source and target property, e.g. from
 * {@code String Source#foo} to {@code int Target#bar}. Name and type of source
 * and target property can differ. If they have different types, the mapping
 * must either refer to a mapping method or a conversion.
 *
 * @author Gunnar Morling
 */
public class PropertyMapping extends ModelElement {

    private final String sourceBeanName;

    private final String targetAccessorName;
    private final Type targetType;

    private final Assignment assignment;

    public static class PropertyMappingBuilder {

        private MappingBuilderContext ctx;
        private SourceMethod method;
        private ExecutableElement targetAccessor;
        private String targetPropertyName;
        private Parameter parameter;

        public PropertyMappingBuilder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public PropertyMappingBuilder souceMethod( SourceMethod sourceMethod ) {
            this.method = sourceMethod;
            return this;
        }

        public PropertyMappingBuilder targetAccessor( ExecutableElement targetAccessor ) {
            this.targetAccessor = targetAccessor;
            return this;
        }

        public PropertyMappingBuilder targetPropertyName( String targetPropertyName ) {
            this.targetPropertyName = targetPropertyName;
            return this;
        }

        public PropertyMappingBuilder parameter( Parameter parameter ) {
            this.parameter = parameter;
            return this;
        }

        private enum TargetAccessorType {

            GETTER,
            SETTER,
            ADDER
        }

        public PropertyMapping build() {

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
                            return getPropertyMapping( sourceAccessor, dateFormat, qualifiers );
                        }
                    }
                }
                else if ( Executables.getPropertyName( sourceAccessor ).equals( sourcePropertyName ) ) {
                    return getPropertyMapping( sourceAccessor, dateFormat, qualifiers );
                }
            }
            return null;
        }

       private PropertyMapping getPropertyMapping( ExecutableElement sourceAccessor,
               String dateFormat,
               List<TypeMirror> qualifiers ) {

            Type sourceType;
            Type targetType;
            TargetAccessorType targetAccessorType;
            String sourceReference = parameter.getName() + "." + sourceAccessor.getSimpleName().toString() + "()";
            String iteratorReference = null;
            boolean sourceIsCollection = false;
            if ( Executables.isSetterMethod( targetAccessor ) ) {
                sourceType = ctx.getTypeFactory().getReturnType( sourceAccessor );
                targetType = ctx.getTypeFactory().getSingleParameter( targetAccessor ).getType();
                targetAccessorType = TargetAccessorType.SETTER;
            }
            else if ( Executables.isAdderMethod( targetAccessor ) ) {
                sourceType = ctx.getTypeFactory().getReturnType( sourceAccessor );
                if ( sourceType.isCollectionType() ) {
                    sourceIsCollection = true;
                    sourceType = sourceType.getTypeParameters().get( 0 );
                    iteratorReference = Executables.getElementNameForAdder( targetAccessor );
                }
                targetType = ctx.getTypeFactory().getSingleParameter( targetAccessor ).getType();
                targetAccessorType = TargetAccessorType.ADDER;
            }
            else {
                sourceType = ctx.getTypeFactory().getReturnType( sourceAccessor );
                targetType = ctx.getTypeFactory().getReturnType( targetAccessor );
                targetAccessorType = TargetAccessorType.GETTER;
            }
            String sourcePropertyName = Executables.getPropertyName( sourceAccessor );
            String mappedElement = "property '" + sourcePropertyName + "'";

            Assignment assignment = ctx.getMappingResolver().getTargetAssignment( method,
                    mappedElement,
                    sourceType,
                    targetType,
                    targetPropertyName,
                    dateFormat,
                    qualifiers,
                    iteratorReference != null ? iteratorReference : sourceReference
            );

            if ( assignment == null ) {
                assignment = forgeMapping( sourceType, targetType, sourceReference, method.getExecutable() );
            }

            if ( assignment != null ) {

                if ( targetType.isCollectionOrMapType() ) {

                    // wrap the setter in the collection / map initializers
                    if ( targetAccessorType == TargetAccessorType.SETTER ) {

                        // wrap the assignment in a new Map or Collection implementation if this is not done in a
                        // mapping method. Note, typeconversons do not apply to collections or maps
                        Assignment newCollectionOrMap = null;
                        if ( assignment.getType() == DIRECT ) {
                            newCollectionOrMap =
                                    new NewCollectionOrMapWrapper( assignment, targetType.getImportTypes() );
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
                            assignment = new AdderWrapper(
                                    assignment,
                                    method.getThrownTypes(),
                                    sourceReference,
                                    sourceType
                            );
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
                ctx.getMessager().printMessage(
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

        private Assignment forgeMapping(Type sourceType, Type targetType, String sourceReference,
                                        ExecutableElement element) {

            Assignment assignment = null;
            if ( sourceType.isCollectionType() && targetType.isCollectionType() ) {

                ForgedMethod methodToGenerate = new ForgedMethod( sourceType, targetType, element );
                IterableMappingMethod.Builder builder = new IterableMappingMethod.Builder( );


                IterableMappingMethod iterableMappingMethod = builder
                        .mappingContext( ctx )
                        .method( methodToGenerate )
                        .build();

                if ( !ctx.getMappingsToGenerate().contains( iterableMappingMethod ) ) {
                    ctx.getMappingsToGenerate().add( iterableMappingMethod );
                }
                assignment = AssignmentFactory.createMethodReference( methodToGenerate, null, targetType );
                assignment.setAssignment( AssignmentFactory.createSimple( sourceReference ) );

            }
            else if ( sourceType.isMapType() && targetType.isMapType() ) {

                ForgedMethod methodToGenerate = new ForgedMethod( sourceType, targetType, element );

                MapMappingMethod.Builder builder = new MapMappingMethod.Builder( );
                MapMappingMethod mapMappingMethod = builder
                        .mappingContext( ctx )
                        .method( methodToGenerate )
                        .build();

                if ( !ctx.getMappingsToGenerate().contains( mapMappingMethod ) ) {
                    ctx.getMappingsToGenerate().add( mapMappingMethod );
                }
                assignment = AssignmentFactory.createMethodReference( methodToGenerate, null, targetType );
                assignment.setAssignment( AssignmentFactory.createSimple( sourceReference ) );
            }
            return assignment;
        }
    }

    public static class ConstantMappingBuilder {

        private MappingBuilderContext ctx;
        private SourceMethod method;
        private String constantExpression;
        private ExecutableElement targetAccessor;
        private String dateFormat;
        private List<TypeMirror> qualifiers;

        public ConstantMappingBuilder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public ConstantMappingBuilder sourceMethod( SourceMethod sourceMethod ) {
            this.method = sourceMethod;
            return this;
        }

        public ConstantMappingBuilder constantExpression( String constantExpression ) {
            this.constantExpression = constantExpression;
            return this;
        }

        public ConstantMappingBuilder targetAccessor( ExecutableElement targetAccessor ) {
            this.targetAccessor = targetAccessor;
            return this;
        }

        public ConstantMappingBuilder dateFormat( String dateFormat ) {
            this.dateFormat = dateFormat;
            return this;
        }

        public ConstantMappingBuilder qualifiers( List<TypeMirror> qualifiers ) {
            this.qualifiers = qualifiers;
            return this;
        }

        public PropertyMapping build() {

            // source
            String mappedElement = "constant '" + constantExpression + "'";
            Type sourceType = ctx.getTypeFactory().getType( String.class );

            // target
            Type targetType;
            if ( Executables.isSetterMethod( targetAccessor ) ) {
                targetType = ctx.getTypeFactory().getSingleParameter( targetAccessor ).getType();
            }
            else {
                targetType = ctx.getTypeFactory().getReturnType( targetAccessor );
            }

            String targetPropertyName = Executables.getPropertyName( targetAccessor );

            Assignment assignment = ctx.getMappingResolver().getTargetAssignment( method,
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
                ctx.getMessager().printMessage(
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
    }

    public static class JavaExpressionMappingBuilder {

        private MappingBuilderContext ctx;
        private SourceMethod method;
        private String javaExpression;
        private ExecutableElement targetAccessor;

       public JavaExpressionMappingBuilder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public JavaExpressionMappingBuilder souceMethod( SourceMethod sourceMethod ) {
            this.method = sourceMethod;
            return this;
        }

        public JavaExpressionMappingBuilder javaExpression( String javaExpression ) {
            this.javaExpression = javaExpression;
            return this;
        }

        public JavaExpressionMappingBuilder targetAccessor( ExecutableElement targetAccessor ) {
            this.targetAccessor = targetAccessor;
            return this;
        }

        public PropertyMapping build() {

            Assignment assignment = AssignmentFactory.createSimple( javaExpression );
            assignment = new SetterWrapper( assignment, method.getThrownTypes() );

            Type targetType;
            if ( Executables.isSetterMethod( targetAccessor ) ) {
                targetType = ctx.getTypeFactory().getSingleParameter( targetAccessor ).getType();
            }
            else {
                targetType = ctx.getTypeFactory().getReturnType( targetAccessor );

                // target accessor is getter, so wrap the setter in getter map/ collection handling
                assignment = new GetterCollectionOrMapWrapper( assignment );
            }

            return new PropertyMapping( targetAccessor.getSimpleName().toString(), targetType, assignment );
        }


    }


    // Constructor for creating mappings of constant expressions.
    private PropertyMapping(String targetAccessorName, Type targetType, Assignment propertyAssignment) {
        this( null, targetAccessorName, targetType, propertyAssignment );
    }

    private PropertyMapping(String sourceBeanName, String targetAccessorName, Type targetType, Assignment assignment) {

        this.sourceBeanName = sourceBeanName;

        this.targetAccessorName = targetAccessorName;
        this.targetType = targetType;

        this.assignment = assignment;
    }

    public String getSourceBeanName() {
        return sourceBeanName;
    }

    public String getTargetAccessorName() {
        return targetAccessorName;
    }

    public Type getTargetType() {
        return targetType;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    @Override
    public Set<Type> getImportTypes() {
        return assignment.getImportTypes();
    }

    @Override
    public String toString() {
        return "PropertyMapping {" +
            "\n    targetName='" + targetAccessorName + "\'," +
            "\n    targetType=" + targetType + "," +
            "\n    propertyAssignment=" + assignment +
            "\n}";
    }
}
