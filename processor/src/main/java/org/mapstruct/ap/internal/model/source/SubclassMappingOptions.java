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

import org.mapstruct.ap.internal.gem.SubclassMappingGem;
import org.mapstruct.ap.internal.gem.SubclassMappingsGem;
import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.TypeUtils;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_ILLEGAL_SUBCLASS;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_METHOD_SIGNATURE_NOT_SUPPORTED;
import static org.mapstruct.ap.internal.util.Message.SUBCLASSMAPPING_NO_VALID_SUPERCLASS;

/**
 * Represents a sub class mapping as configured via {@code @SubclassMapping}.
 *
 * @author Ben Zegveld
 */
public class SubclassMappingOptions extends DelegatingOptions {

    private TypeMirror sourceClass;
    private TypeMirror targetClass;

    public SubclassMappingOptions(TypeMirror sourceClass, TypeMirror targetClass, DelegatingOptions next) {
        super( next );
        this.sourceClass = sourceClass;
        this.targetClass = targetClass;
    }

    @Override
    public boolean hasAnnotation() {
        return sourceClass != null && targetClass != null;
    }

    private static boolean isConsistent(SubclassMappingGem gem, ExecutableElement method, FormattingMessager messager,
                                        TypeUtils typeUtils, List<Parameter> parameters, Type resultType) {

        if ( method.getReturnType().getKind() == TypeKind.VOID ) {
            messager.printMessage( method, gem.mirror(), SUBCLASSMAPPING_METHOD_SIGNATURE_NOT_SUPPORTED );
            return false;
        }

        TypeMirror sourceSubclass = gem.source().getValue();
        TypeMirror targetSubclass = gem.target().getValue();
        TypeMirror targetParentType = resultType.getTypeMirror();
        validateTypeMirrors( sourceSubclass, targetSubclass, targetParentType );

        boolean isConsistent = true;

        boolean isChildOfAParameter = false;
        for ( Parameter parameter : Parameter.getSourceParameters( parameters ) ) {
            TypeMirror sourceParentType = parameter.getType().getTypeMirror();
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

    public TypeMirror getSourceClass() {
        return sourceClass;
    }

    public TypeMirror getTargetClass() {
        return targetClass;
    }

    public static void addInstances(SubclassMappingsGem gem, ExecutableElement method,
                                    BeanMappingOptions beanMappingOptions, FormattingMessager messager,
                                    TypeUtils typeUtils, Set<SubclassMappingOptions> mappings,
                                    List<Parameter> parameters, Type resultType) {
        for ( SubclassMappingGem subclassMappingGem : gem.value().get() ) {
            addInstance(
                subclassMappingGem,
                method,
                beanMappingOptions,
                messager,
                typeUtils,
                mappings,
                parameters,
                resultType );
        }
    }

    public static void addInstance(SubclassMappingGem subclassMapping, ExecutableElement method,
                                   BeanMappingOptions beanMappingOptions, FormattingMessager messager,
                                   TypeUtils typeUtils, Set<SubclassMappingOptions> mappings,
                                   List<Parameter> parameters, Type resultType) {
        if ( !isConsistent( subclassMapping, method, messager, typeUtils, parameters, resultType ) ) {
            return;
        }

        TypeMirror sourceSubclass = subclassMapping.source().getValue();
        TypeMirror targetSubclass = subclassMapping.target().getValue();

        mappings.add( new SubclassMappingOptions( sourceSubclass, targetSubclass, beanMappingOptions ) );
    }
}
