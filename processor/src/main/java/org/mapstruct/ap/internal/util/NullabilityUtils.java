/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util;

import java.util.List;
import java.util.function.BooleanSupplier;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

/**
 * Utility for detecting JSpecify nullness annotations on elements and computing
 * whether a null check is required for a source-to-target property mapping.
 *
 * @author Filip Hrisafov
 */
public final class NullabilityUtils {

    /**
     * Represents the nullability state of a source or target element.
     */
    public enum Nullability {
        /**
         * The element is annotated with {@code @NonNull}.
         */
        NON_NULL,
        /**
         * The element is annotated with {@code @Nullable}.
         */
        NULLABLE,
        /**
         * No JSpecify nullability annotation is present on the element.
         */
        UNKNOWN
    }

    private NullabilityUtils() {
    }

    /**
     * Determines the nullability of an accessor element based on JSpecify annotations.
     * <p>
     * For getter methods ({@link ExecutableElement}), this checks the return type's annotations
     * since JSpecify annotations are {@code TYPE_USE} annotations placed on the return type.
     * For setter parameters ({@link VariableElement}), this checks the parameter type's annotations.
     * For fields ({@link VariableElement}), this checks the field type's annotations.
     * <p>
     * If no direct annotation is found, {@code enclosingTypeNullMarked} is consulted — when it
     * returns {@code true}, unannotated types are effectively {@code @NonNull}. The supplier is
     * evaluated lazily and only when no direct annotation was found.
     *
     * @param element                 the accessor element to inspect (getter method, setter parameter, or field)
     * @param enclosingTypeNullMarked supplier for whether the enclosing type is in a {@code @NullMarked} scope
     * @return the nullability state
     */
    public static Nullability getNullability(Element element, BooleanSupplier enclosingTypeNullMarked) {
        if ( element == null ) {
            return Nullability.UNKNOWN;
        }

        // JSpecify annotations are TYPE_USE annotations.
        // For getters: annotation is on the return type
        // For setter parameters / fields: annotation is on the variable type
        if ( element instanceof ExecutableElement ) {
            // Getter method — check the return type annotations
            TypeMirror returnType = ( (ExecutableElement) element ).getReturnType();
            Nullability result = getNullabilityFromTypeMirror( returnType );
            if ( result != Nullability.UNKNOWN ) {
                return result;
            }
        }
        else if ( element instanceof VariableElement ) {
            // Setter parameter or field — check the variable type annotations
            TypeMirror type = element.asType();
            Nullability result = getNullabilityFromTypeMirror( type );
            if ( result != Nullability.UNKNOWN ) {
                return result;
            }
        }

        // Fallback: check the element itself for declaration-level annotations.
        // This covers nullability annotations that target METHOD, PARAMETER, or FIELD
        // without TYPE_USE (e.g. javax.annotation.Nonnull).
        Nullability fromElement = getNullabilityFromAnnotationMirrors( element.getAnnotationMirrors() );
        if ( fromElement != Nullability.UNKNOWN ) {
            return fromElement;
        }

        // No direct annotation found — in a @NullMarked scope, unannotated types are effectively @NonNull.
        if ( enclosingTypeNullMarked.getAsBoolean() ) {
            return Nullability.NON_NULL;
        }

        return Nullability.UNKNOWN;
    }

    /**
     * Determines the nullability of a setter element based on its first parameter's JSpecify annotations.
     * <p>
     * For setter methods, the nullability annotation is on the parameter type, not the method itself.
     *
     * @param element                 the setter method element
     * @param enclosingTypeNullMarked supplier for whether the enclosing type is in a {@code @NullMarked} scope
     * @return the nullability state of the setter's parameter
     */
    public static Nullability getSetterNullability(Element element, BooleanSupplier enclosingTypeNullMarked) {
        if ( element instanceof ExecutableElement ) {
            List<? extends VariableElement> parameters = ( (ExecutableElement) element ).getParameters();
            if ( !parameters.isEmpty() ) {
                return getNullability( parameters.get( 0 ), enclosingTypeNullMarked );
            }
        }
        // For fields, fall back to the element's own nullability
        return getNullability( element, enclosingTypeNullMarked );
    }

    private static Nullability getNullabilityFromTypeMirror(TypeMirror typeMirror) {
        if ( typeMirror == null ) {
            return Nullability.UNKNOWN;
        }
        return getNullabilityFromAnnotationMirrors( typeMirror.getAnnotationMirrors() );
    }

    private static Nullability getNullabilityFromAnnotationMirrors(
        List<? extends AnnotationMirror> annotationMirrors) {
        for ( AnnotationMirror mirror : annotationMirrors ) {
            String fqn = ( (TypeElement) mirror.getAnnotationType().asElement() ).getQualifiedName().toString();
            if ( JSpecifyConstants.NON_NULL_FQN.equals( fqn ) ) {
                return Nullability.NON_NULL;
            }
            if ( JSpecifyConstants.NULLABLE_FQN.equals( fqn ) ) {
                return Nullability.NULLABLE;
            }
        }
        return Nullability.UNKNOWN;
    }

    /**
     * Determines whether a null check is required for a property mapping based on JSpecify annotations
     * on the source and target elements.
     * <p>
     * Only returns a non-null decision for the clear-cut cases:
     * source {@code @NonNull} (skip check) or target {@code @NonNull} (always check).
     * All other cases return {@code null} to defer to the existing {@code NullValueCheckStrategy}.
     *
     * @param sourceNullability the nullability of the source (getter return type / parameter)
     * @param targetNullability the nullability of the target (setter parameter / field)
     * @return {@code Boolean.TRUE} if a null check is needed, {@code Boolean.FALSE} if it should be skipped,
     * or {@code null} if JSpecify annotations are not present and the existing strategy should be used
     */
    public static Boolean requiresNullCheck(Nullability sourceNullability, Nullability targetNullability) {
        if ( sourceNullability == Nullability.NON_NULL ) {
            // Source is guaranteed non-null, no null check needed
            return Boolean.FALSE;
        }
        if ( targetNullability == Nullability.NON_NULL ) {
            // Target requires non-null: always check (regardless of source annotation)
            return Boolean.TRUE;
        }
        // All other cases: defer to existing NullValueCheckStrategy
        return null;
    }
}
