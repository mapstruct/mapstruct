/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.SubClassMappingGem;
import org.mapstruct.ap.internal.gem.SubClassMappingsGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.TypeUtils;

import static java.util.Objects.nonNull;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_ILLEGAL_SUBCLASS;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_METHOD_SIGNATURE_NOT_SUPPORTED;

/**
 * Represents a sub class mapping as configured via {@code @SubClassMapping}.
 *
 * @author Ben Zegveld
 */
public class SubClassMappingOptions extends DelegatingOptions {

    private TypeMirror sourceClass;
    private TypeMirror targetClass;

    public SubClassMappingOptions(TypeMirror sourceClass, TypeMirror targetClass, DelegatingOptions next) {
        super( next );
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    @Override
    public boolean hasAnnotation() {
        return sourceClass != null && targetClass != null;
    }

    public static List<SubClassMappingOptions> getInstanceOn(ExecutableElement method, MapperOptions mapperOptions,
                                                       FormattingMessager messager, TypeUtils typeUtils) {

        SubClassMappingsGem subClassMappings = SubClassMappingsGem.instanceOn( method );
        List<SubClassMappingGem> subClassMappingAnnotations;
        if ( nonNull( subClassMappings ) ) {
            subClassMappingAnnotations = subClassMappings.value().get();
        }
        else if ( nonNull( SubClassMappingGem.instanceOn( method ) ) ) {
            subClassMappingAnnotations = Arrays.asList( SubClassMappingGem.instanceOn( method ) );
        }
        else {
            return Collections.emptyList();
        }

        List<SubClassMappingOptions> subClassMappingOptions = new ArrayList<>();

        for ( SubClassMappingGem subClassMapping : subClassMappingAnnotations ) {

            if ( !isConsistent( subClassMapping, method, messager, typeUtils ) ) {
                continue;
            }

            TypeMirror sourceSubClass = subClassMapping.source().getValue();
            TypeMirror targetSubClass = subClassMapping.target().getValue();

            subClassMappingOptions
                                  .add(
                                      new SubClassMappingOptions(
                                          sourceSubClass,
                                          targetSubClass,
                                          mapperOptions ) );
        }
        return subClassMappingOptions;
    }

    private static boolean isConsistent(SubClassMappingGem gem, ExecutableElement method, FormattingMessager messager,
                                        TypeUtils typeUtils) {

        if ( method.getParameters().isEmpty() || method.getReturnType().getKind() == TypeKind.VOID ) {
            messager
                    .printMessage(
                        method,
                        gem.mirror(),
                        SUBCLASSMAPPING_METHOD_SIGNATURE_NOT_SUPPORTED );
            return false;
        }

        TypeMirror sourceSubClass = gem.source().getValue();
        TypeMirror targetSubClass = gem.target().getValue();
        TypeMirror sourceParentType = method.getParameters().get( 0 ).asType();
        TypeMirror targetParentType = method.getReturnType();
        boolean isConsistent = true;

        if ( !isChildOfParent( typeUtils, sourceSubClass, sourceParentType ) ) {
            messager
                    .printMessage(
                        method,
                        gem.mirror(),
                        SUBCLASSMAPPING_ILLEGAL_SUBCLASS,
                        sourceParentType.toString(),
                        sourceSubClass.toString() );
            isConsistent = false;
        }
        if ( !isChildOfParent( typeUtils, targetSubClass, targetParentType ) ) {
            messager
                    .printMessage(
                        method,
                        gem.mirror(),
                        SUBCLASSMAPPING_ILLEGAL_SUBCLASS,
                        targetParentType.toString(),
                        targetSubClass.toString() );
            isConsistent = false;
        }
        return isConsistent;
    }

    private static boolean isChildOfParent(TypeUtils typeUtils, TypeMirror childType, TypeMirror parentType) {
        return typeUtils.isSubtype( childType, parentType );
    }

    public TypeMirror getSourceClass() {
        return sourceClass;
    }

    public TypeMirror getTargetClass() {
        return targetClass;
    }
}
