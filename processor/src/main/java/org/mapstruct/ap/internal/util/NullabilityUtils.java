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
import javax.lang.model.element.ElementKind;
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
     * invoked at most once, and only if no JSpecify annotation was found on the element or its type.
     *
     * @param element                 the accessor element to inspect (getter method, setter parameter, or field);
     *                                may be {@code null} in which case {@link Nullability#UNKNOWN} is returned
     * @param enclosingTypeNullMarked supplier for whether the enclosing bean type is in a
     *                                {@code @NullMarked} scope; must be non-{@code null}
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

        // Fallback: check declaration-level annotation mirrors. Some compilers (notably ECJ)
        // surface JSpecify TYPE_USE annotations on the element rather than the type mirror.
        Nullability fromElement = getNullabilityFromAnnotationMirrors( element.getAnnotationMirrors() );
        if ( fromElement != Nullability.UNKNOWN ) {
            return fromElement;
        }

        // Walk enclosing elements up to the declaring type to honor method-level
        // @NullMarked / @NullUnmarked (e.g. a @NullUnmarked method inside a @NullMarked class
        // must revert unannotated types back to unknown nullability).
        Boolean elementScope = resolveElementScope( element );
        if ( elementScope != null ) {
            return elementScope ? Nullability.NON_NULL : Nullability.UNKNOWN;
        }

        // No element-level scope — consult the enclosing bean type's @NullMarked scope.
        if ( enclosingTypeNullMarked.getAsBoolean() ) {
            return Nullability.NON_NULL;
        }

        return Nullability.UNKNOWN;
    }

    /**
     * Walks from {@code element} up to (but not including) its declaring {@link TypeElement},
     * checking for {@code @NullMarked} / {@code @NullUnmarked} on intermediate elements
     * (e.g. the enclosing method of a parameter, or the element itself for a field / getter).
     *
     * @return {@code TRUE} when a closer {@code @NullMarked} is found, {@code FALSE} when a
     * closer {@code @NullUnmarked} is found, or {@code null} when neither is present before
     * the declaring type is reached (leaving the bean-type scope to decide).
     */
    private static Boolean resolveElementScope(Element element) {
        Element current = element;
        while ( current != null && !isTypeElement( current ) ) {
            Boolean scope = findScopeAnnotation( current );
            if ( scope != null ) {
                return scope;
            }
            current = current.getEnclosingElement();
        }
        return null;
    }

    private static boolean isTypeElement(Element element) {
        ElementKind kind = element.getKind();
        return kind.isClass() || kind.isInterface();
    }

    private static Boolean findScopeAnnotation(Element element) {
        for ( AnnotationMirror mirror : element.getAnnotationMirrors() ) {
            Element annotationElement = mirror.getAnnotationType().asElement();
            if ( !( annotationElement instanceof TypeElement ) ) {
                continue;
            }
            String fqn = ( (TypeElement) annotationElement ).getQualifiedName().toString();
            if ( JSpecifyConstants.NULL_MARKED_FQN.equals( fqn ) ) {
                return Boolean.TRUE;
            }
            if ( JSpecifyConstants.NULL_UNMARKED_FQN.equals( fqn ) ) {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    /**
     * Determines the nullability of a write-accessor element — either a setter method or a field.
     * <p>
     * For setter methods ({@link ExecutableElement}) the nullability annotation lives on the
     * parameter type, not on the method itself, so the first parameter's nullability is returned.
     * For fields (or any other element type) the element's own type nullability is returned.
     *
     * @param element                 the write-accessor element (setter or field); may be
     *                                {@code null} in which case {@link Nullability#UNKNOWN} is returned
     * @param enclosingTypeNullMarked supplier for whether the enclosing bean type is in a
     *                                {@code @NullMarked} scope; must be non-{@code null}
     * @return the nullability state of the setter's parameter or of the field
     */
    public static Nullability getSetterNullability(Element element, BooleanSupplier enclosingTypeNullMarked) {
        if ( element instanceof ExecutableElement ) {
            List<? extends VariableElement> parameters = ( (ExecutableElement) element ).getParameters();
            if ( parameters.isEmpty() ) {
                // A zero-parameter method is not a valid write accessor. Falling through to
                // getNullability would inspect the return type, which is meaningless here.
                return Nullability.UNKNOWN;
            }
            return getNullability( parameters.get( 0 ), enclosingTypeNullMarked );
        }
        // Field or other write accessor: consult the element's own type nullability.
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
            Element annotationElement = mirror.getAnnotationType().asElement();
            if ( !( annotationElement instanceof TypeElement ) ) {
                // Defensive: during incremental builds the annotation may be an ErrorType
                // whose element is not a TypeElement. Skip instead of failing with a CCE.
                continue;
            }
            String fqn = ( (TypeElement) annotationElement ).getQualifiedName().toString();
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
