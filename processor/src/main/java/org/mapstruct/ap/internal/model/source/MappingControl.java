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

import org.mapstruct.ap.internal.gem.MappingControlGem;
import org.mapstruct.ap.internal.gem.MappingControlUseGem;
import org.mapstruct.ap.internal.gem.MappingControlsGem;

public class MappingControl {

    private static final String JAVA_LANG_ANNOTATION_PGK = "java.lang.annotation";
    private static final String ORG_MAPSTRUCT_PKG = "org.mapstruct";
    private static final String MAPPING_CONTROL_FQN = "org.mapstruct.control.MappingControl";
    private static final String MAPPING_CONTROLS_FQN = "org.mapstruct.control.MappingControls";

    private boolean allowDirect = false;
    private boolean allowTypeConversion = false;
    private boolean allowMappingMethod = false;
    private boolean allow2Steps = false;

    public static MappingControl fromTypeMirror(TypeMirror mirror, Elements elementUtils) {
        MappingControl mappingControl = new MappingControl();
        if ( TypeKind.DECLARED == mirror.getKind() ) {
            resolveControls( mappingControl, ( (DeclaredType) mirror ).asElement(), new HashSet<>(), elementUtils );
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

    private static void resolveControls(MappingControl control, Element element, Set<Element> handledElements,
                                       Elements elementUtils) {
        for ( AnnotationMirror annotationMirror : element.getAnnotationMirrors() ) {
            Element lElement = annotationMirror.getAnnotationType().asElement();
            if ( isAnnotation( lElement, MAPPING_CONTROL_FQN ) ) {
                determineMappingControl( control, MappingControlGem.instanceOn( element ) );
            }
            else if ( isAnnotation( lElement, MAPPING_CONTROLS_FQN ) ) {
                MappingControlsGem.instanceOn( element )
                    .value()
                    .get()
                    .forEach( m -> determineMappingControl( control, m ) );
            }
            else if ( !isAnnotationInPackage( lElement, JAVA_LANG_ANNOTATION_PGK, elementUtils )
                && !isAnnotationInPackage( lElement, ORG_MAPSTRUCT_PKG, elementUtils )
                && !handledElements.contains( lElement )
            ) {
                // recur over annotation mirrors
                handledElements.add( lElement );
                resolveControls( control, lElement, handledElements, elementUtils );
            }
        }
    }

    private static void determineMappingControl(MappingControl in, MappingControlGem gem) {
        MappingControlUseGem use = MappingControlUseGem.valueOf( gem.value().get() );
        switch ( use ) {
            case DIRECT:
                in.allowDirect = true;
                break;
            case MAPPING_METHOD:
                in.allowMappingMethod = true;
                break;
            case BUILT_IN_CONVERSION:
                in.allowTypeConversion = true;
                break;
            case COMPLEX_MAPPING:
                in.allow2Steps = true;
                break;
            default:
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
