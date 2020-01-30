/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;

public class MappingControl {

    private static final String JAVA_LANG_ANNOTATION_PGK = "java.lang.annotation";
    private static final String ORG_MAPSTRUCT_PKG = "org.mapstruct";
    private static final String ALLOW_DIRECT_FQN = "org.mapstruct.control.AllowDirect";
    private static final String ALLOW_CONVERSION_FQN = "org.mapstruct.control.AllowBuiltInConversion";
    private static final String ALLOW_MAPPING_METHOD_FQN = "org.mapstruct.control.AllowMappingMethod";
    private static final String ALLOW_2_STEPS_FQN = "org.mapstruct.control.AllowComplexMapping";

    private boolean allowDirect = false;
    private boolean allowTypeConversion = false;
    private boolean allowMappingMethod = false;
    private boolean allow2Steps = false;

    public static MappingControl fromTypeMirror(TypeMirror mirror, Elements elementUtils) {
        MappingControl mappingControl = new MappingControl();
        if ( TypeKind.DECLARED == mirror.getKind() ) {
            resolveControl( mappingControl, ( (DeclaredType) mirror ).asElement(), new HashSet<>(), elementUtils );
        }
        return mappingControl;
    }

    private MappingControl() {
    }

    public boolean allowDirect() {
        return allowDirect;
    }

    public boolean allowTypeConversion() {
        return allowTypeConversion;
    }

    public boolean allowMappingMethod() {
        return allowMappingMethod;
    }

    public boolean allowBy2Steps() {
        return allow2Steps;
    }

    private static void resolveControl(MappingControl control, Element element, Set<Element> handledElements,
                                        Elements elementUtils) {
        if ( isAnnotation( element, ALLOW_DIRECT_FQN ) ) {
            control.allowDirect = true;
        }
        else if ( isAnnotation( element, ALLOW_CONVERSION_FQN ) ) {
            control.allowTypeConversion = true;
        }
        else if ( isAnnotation( element, ALLOW_MAPPING_METHOD_FQN ) ) {
            control.allowMappingMethod = true;
        }
        else if ( isAnnotation( element, ALLOW_2_STEPS_FQN ) ) {
            control.allow2Steps = true;
        }
        else if ( !isAnnotationInPackage( element, JAVA_LANG_ANNOTATION_PGK, elementUtils )
            && !isAnnotationInPackage( element, ORG_MAPSTRUCT_PKG, elementUtils )
            && !handledElements.contains( element )
        ) {
            // recur over annotation mirrors
            handledElements.add( element );
            resolveControls( control, element, handledElements, elementUtils );
        }
    }

    private static void resolveControls(MappingControl control, Element element, Set<Element> handledElements,
                                        Elements elementUtils) {

        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element lElement = annotationMirror.getAnnotationType().asElement();
            resolveControl( control, lElement, handledElements, elementUtils );
        }
    }

    private static boolean isAnnotationInPackage(Element element, String packageFQN, Elements elementUtils) {
        if ( ElementKind.ANNOTATION_TYPE == element.getKind() ) {
            return packageFQN.equals( elementUtils.getPackageOf( element ).getQualifiedName().toString() );
        }
        return false;
    }

    private static boolean isAnnotation(Element element, String annotationFQN) {
        if ( ElementKind.ANNOTATION_TYPE == element.getKind() ) {
            return annotationFQN.equals( ( (TypeElement) element ).getQualifiedName().toString() );
        }
        return false;
    }

}
