/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.SubclassMappingGem;
import org.mapstruct.ap.internal.gem.SubclassMappingsGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_ILLEGAL_SUBCLASS;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_NO_VALID_SUPERCLASS;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_UPDATE_METHODS_NOT_SUPPORTED;

/**
 * Represents a subclass mapping as configured via {@code @SubclassMapping}.
 *
 * @author Ben Zegveld
 */
public class SubclassMappingOptions extends DelegatingOptions {

    private final TypeMirror source;
    private final TypeMirror target;
    private final TypeUtils typeUtils;
    private final SelectionParameters selectionParameters;
    private final SubclassMappingGem subclassMapping;

    public SubclassMappingOptions(TypeMirror source, TypeMirror target, TypeUtils typeUtils, DelegatingOptions next,
                                  SelectionParameters selectionParameters, SubclassMappingGem subclassMapping) {
        super( next );
        this.source = source;
        this.target = target;
        this.typeUtils = typeUtils;
        this.selectionParameters = selectionParameters;
        this.subclassMapping = subclassMapping;
    }

    @Override
    public boolean hasAnnotation() {
        return source != null && target != null;
    }

    private static boolean isConsistent(SubclassMappingGem gem, ExecutableElement method, FormattingMessager messager,
                                        TypeUtils typeUtils, List<Parameter> sourceParameters, Type resultType,
                                        SubclassValidator subclassValidator) {

        if ( resultType == null ) {
            messager.printMessage( method, gem.mirror(), SUBCLASSMAPPING_UPDATE_METHODS_NOT_SUPPORTED );
            return false;
        }

        TypeMirror sourceSubclass = gem.source().getValue();
        TypeMirror targetSubclass = gem.target().getValue();
        TypeMirror targetParentType = resultType.getTypeMirror();
        validateTypeMirrors( sourceSubclass, targetSubclass, targetParentType );

        boolean isConsistent = true;

        boolean isChildOfAParameter = false;
        for ( Parameter sourceParameter : sourceParameters ) {
            TypeMirror sourceParentType = sourceParameter.getType().getTypeMirror();
            validateTypeMirrors( sourceParentType );
            isChildOfAParameter = isChildOfAParameter || isChildOfParent( typeUtils, sourceSubclass, sourceParentType );
        }
        if ( !isChildOfAParameter ) {
            messager
                    .printMessage(
                        method,
                        gem.mirror(),
                        SUBCLASSMAPPING_NO_VALID_SUPERCLASS,
                        sourceSubclass.toString() );
            isConsistent = false;
        }
        if ( !isChildOfParent( typeUtils, targetSubclass, targetParentType ) ) {
            messager
                    .printMessage(
                        method,
                        gem.mirror(),
                        SUBCLASSMAPPING_ILLEGAL_SUBCLASS,
                        targetParentType.toString(),
                        targetSubclass.toString() );
            isConsistent = false;
        }
        if ( !subclassValidator.isValidUsage( method, gem.mirror(), sourceSubclass ) ) {
            isConsistent = false;
        }
        return isConsistent;
    }

    private static void validateTypeMirrors(TypeMirror... typeMirrors) {
        for ( TypeMirror typeMirror : typeMirrors ) {
            if ( typeMirror == null ) {
                // When a class used in uses or imports is created by another annotation processor
                // then javac will not return correct TypeMirror with TypeKind#ERROR, but rather a string "<error>"
                // the gem tools would return a null TypeMirror in that case.
                // Therefore throw TypeHierarchyErroneousException so we can postpone the generation of the mapper
                throw new TypeHierarchyErroneousException( typeMirror );
            }
        }
    }

    private static boolean isChildOfParent(TypeUtils typeUtils, TypeMirror childType, TypeMirror parentType) {
        return typeUtils.isSubtype( childType, parentType );
    }

    public TypeMirror getSource() {
        return source;
    }

    public TypeMirror getTarget() {
        return target;
    }

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public AnnotationMirror getMirror() {
        return Optional.ofNullable( subclassMapping ).map( SubclassMappingGem::mirror ).orElse( null );
    }

    public static void addInstances(SubclassMappingsGem gem, ExecutableElement method,
                                    BeanMappingOptions beanMappingOptions, FormattingMessager messager,
                                    TypeUtils typeUtils, Set<SubclassMappingOptions> mappings,
                                    List<Parameter> sourceParameters, Type resultType,
                                    SubclassValidator subclassValidator) {
        for ( SubclassMappingGem subclassMapping : gem.value().get() ) {
            addInstance(
                subclassMapping,
                method,
                beanMappingOptions,
                messager,
                typeUtils,
                mappings,
                sourceParameters,
                resultType,
                subclassValidator );
        }
    }

    public static void addInstance(SubclassMappingGem subclassMapping, ExecutableElement method,
                                   BeanMappingOptions beanMappingOptions, FormattingMessager messager,
                                   TypeUtils typeUtils, Set<SubclassMappingOptions> mappings,
                                   List<Parameter> sourceParameters, Type resultType,
                                   SubclassValidator subclassValidator) {
        if ( !isConsistent(
            subclassMapping,
            method,
            messager,
            typeUtils,
            sourceParameters,
            resultType,
            subclassValidator ) ) {
            return;
        }

        TypeMirror sourceSubclass = subclassMapping.source().getValue();
        TypeMirror targetSubclass = subclassMapping.target().getValue();
        SelectionParameters selectionParameters = new SelectionParameters(
            subclassMapping.qualifiedBy().get(),
            subclassMapping.qualifiedByName().get(),
            targetSubclass,
            typeUtils
        );

        mappings
                .add(
                    new SubclassMappingOptions(
                        sourceSubclass,
                        targetSubclass,
                        typeUtils,
                        beanMappingOptions,
                        selectionParameters,
                        subclassMapping
                    ) );
    }

    public static List<SubclassMappingOptions> copyForInverseInheritance(Set<SubclassMappingOptions> mappings,
                                                                         BeanMappingOptions beanMappingOptions) {
        // we are not interested in keeping it unique at this point.
        return mappings.stream().map( mapping -> new SubclassMappingOptions(
            mapping.target,
            mapping.source,
            mapping.typeUtils,
            beanMappingOptions,
            mapping.selectionParameters,
            mapping.subclassMapping
        ) ).collect( Collectors.toCollection( ArrayList::new ) );
    }

    public static List<SubclassMappingOptions> copyForInheritance(Set<SubclassMappingOptions> subclassMappings,
                                                                  BeanMappingOptions beanMappingOptions) {
         // we are not interested in keeping it unique at this point.
         List<SubclassMappingOptions> mappings = new ArrayList<>();
         for ( SubclassMappingOptions subclassMapping : subclassMappings ) {
             mappings.add(
                         new SubclassMappingOptions(
                                    subclassMapping.source,
                                    subclassMapping.target,
                                    subclassMapping.typeUtils,
                                    beanMappingOptions,
                                    subclassMapping.selectionParameters,
                                    subclassMapping.subclassMapping ) );
         }
         return mappings;
     }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null || !( obj instanceof SubclassMappingOptions ) ) {
            return false;
        }
        SubclassMappingOptions other = (SubclassMappingOptions) obj;
        return typeUtils.isSameType( source, other.source );
    }

    @Override
    public int hashCode() {
        return 1; // use a stable value because TypeMirror is not safe to use for hashCode.
    }
}
