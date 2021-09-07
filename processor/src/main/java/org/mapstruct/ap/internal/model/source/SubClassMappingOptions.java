/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.List;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.gem.SubClassMappingGem;
import org.mapstruct.ap.internal.gem.SubClassMappingsGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.TypeUtils;

import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_ILLEGAL_SUBCLASS;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_METHOD_SIGNATURE_NOT_SUPPORTED;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_NO_VALID_SUPERCLASS;

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

    private static boolean isConsistent(SubClassMappingGem gem, ExecutableElement method, FormattingMessager messager,
                                        TypeUtils typeUtils, List<Parameter> parameters, Type resultType) {

        if ( method.getReturnType().getKind() == TypeKind.VOID ) {
            messager.printMessage( method, gem.mirror(), SUBCLASSMAPPING_METHOD_SIGNATURE_NOT_SUPPORTED );
            return false;
        }

        TypeMirror sourceSubClass = gem.source().getValue();
        TypeMirror targetSubClass = gem.target().getValue();
        TypeMirror targetParentType = resultType.getTypeMirror();
        boolean isConsistent = true;

        boolean isChildOfAParameter = false;
        for ( Parameter parameter : Parameter.getSourceParameters( parameters ) ) {
            TypeMirror sourceParentType = parameter.getType().getTypeMirror();
            isChildOfAParameter = isChildOfAParameter || isChildOfParent( typeUtils, sourceSubClass, sourceParentType );
        }
        if ( !isChildOfAParameter ) {
            messager
                    .printMessage(
                        method,
                        gem.mirror(),
                        SUBCLASSMAPPING_NO_VALID_SUPERCLASS,
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

    public static void addInstances(SubClassMappingsGem gem, ExecutableElement method,
                                    BeanMappingOptions beanMappingOptions, FormattingMessager messager,
                                    TypeUtils typeUtils, Set<SubClassMappingOptions> mappings,
                                    List<Parameter> parameters, Type resultType) {
        for ( SubClassMappingGem subClassMappingGem : gem.value().get() ) {
            addInstance(
                subClassMappingGem,
                method,
                beanMappingOptions,
                messager,
                typeUtils,
                mappings,
                parameters,
                resultType );
        }
    }

    public static void addInstance(SubClassMappingGem subClassMapping, ExecutableElement method,
                                   BeanMappingOptions beanMappingOptions, FormattingMessager messager,
                                   TypeUtils typeUtils, Set<SubClassMappingOptions> mappings,
                                   List<Parameter> parameters, Type resultType) {
        if ( !isConsistent( subClassMapping, method, messager, typeUtils, parameters, resultType ) ) {
            return;
        }

        TypeMirror sourceSubClass = subClassMapping.source().getValue();
        TypeMirror targetSubClass = subClassMapping.target().getValue();

        mappings.add( new SubClassMappingOptions( sourceSubClass, targetSubClass, beanMappingOptions ) );
    }
}
