/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.util.accessor;

import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

/**
 * Resolver for JSpecify nullness annotations on elements, used to decide whether a null
 * check is required for a source-to-target property mapping.
 * <p>
 * A single instance is created per annotation-processor run and carries the
 * {@link #jSpecifyEnabled} flag derived from the {@code mapstruct.disableJSpecify} option. When
 * disabled, the {@code get*Nullability} methods still return
 * {@link Nullability#voidNullability()} and {@link Nullability#getPrimitiveNullability(TypeMirror)}
 * where applicable, but fall back to {@code (NULLABLE, DEFAULT)} otherwise; the {@code get*Scope}
 * methods return {@link JspecifyNullabilityScope#UNKNOWN}. This causes downstream callers to fall
 * back to the pre-JSpecify {@code NullValueCheckStrategy}-based behavior.
 *
 * @author Filip Hrisafov
 */
public class NullabilityResolver {

     /**
     * Represents the effective nullability of a source or target element.
     */
    public enum JSpecifyNullability {
        /**
         * The element is effectively non-null — either directly annotated {@code @NonNull},
         * or within a {@code @NullMarked} scope without a closer {@code @NullUnmarked} or
         * {@code @Nullable} override.
         */
        NON_NULL,
        /**
         * The element is explicitly annotated {@code @Nullable}.
         */
        NULLABLE,
        /**
         * Nullability is unspecified — no annotation applies and the element is not within
         * an applicable {@code @NullMarked} scope (or JSpecify support is disabled).
         */
        UNKNOWN;

        public Nullability toNull() {
            if ( this == NON_NULL ) {
                return new  Nullability( Nullability.NullabilityState.NON_NULL,
                        Nullability.NullabilityCause.JSPECIFY );
            }
            if ( this == NULLABLE ) {
                return new  Nullability( Nullability.NullabilityState.NULLABLE,
                        Nullability.NullabilityCause.JSPECIFY );
            }
            return new  Nullability( Nullability.NullabilityState.NULLABLE,
                    Nullability.NullabilityCause.DEFAULT );
        }
    }

    private final boolean jSpecifyEnabled;

    public NullabilityResolver(boolean jSpecifyEnabled) {
        this.jSpecifyEnabled = jSpecifyEnabled;
    }

    public boolean isJSpecifyEnabled() {
        return jSpecifyEnabled;
    }

    public Nullability getMethodeReturnTypeNullability(ExecutableElement executableElement) {
        TypeMirror returnType = executableElement.getReturnType();
        TypeKind returnTypeKind = returnType.getKind();
        if ( returnTypeKind == TypeKind.VOID ) {
            return Nullability.voidNullability();
        }
        return Nullability.getPrimitiveNullability( returnType )
                .orElseGet( () -> getNullability( executableElement ).toNull() );
    }

    public Nullability getParameterNullability(VariableElement variableElement) {
        return Nullability.getPrimitiveNullability( variableElement.asType() )
                .orElseGet( () -> getNullability( variableElement ).toNull() );
    }

    public Nullability getFieldNullability(VariableElement field) {
        return Nullability.getPrimitiveNullability( field.asType() )
                .orElseGet( () -> getNullability( field ).toNull() );
    }

    /**
     * Determines the nullability of an accessor element based on JSpecify annotations.
     * <p>
     * For getter methods ({@link ExecutableElement}), this checks the return type's annotations
     * since JSpecify annotations are {@code TYPE_USE} annotations placed on the return type.
     * For setter parameters ({@link VariableElement}), this checks the parameter type's annotations.
     * For fields ({@link VariableElement}), this checks the field type's annotations.
     * <p>
     * If no direct annotation is found, the method walks the enclosing element chain looking
     * for a method-level {@code @NullMarked} / {@code @NullUnmarked}. If nothing is found there
     * either, {@code resolveNullMarked} is consulted on the enclosing element — when it reports a
     * {@code @NullMarked} scope, unannotated types are effectively {@code @NonNull}.
     * <p>
     * When this resolver is disabled, {@link JSpecifyNullability#UNKNOWN} is returned without any
     * inspection.
     *
     * @param element the accessor element to inspect (getter method, setter parameter, or field);
     *                may be {@code null} in which case {@link JSpecifyNullability#UNKNOWN} is returned
     * @return the nullability state
     */
    private JSpecifyNullability getNullability(Element element) {
        if ( !jSpecifyEnabled || element == null ) {
            return JSpecifyNullability.UNKNOWN;
        }

        // JSpecify annotations are TYPE_USE annotations.
        // For getters: annotation is on the return type
        // For setter parameters / fields: annotation is on the variable type
        if ( element instanceof ExecutableElement ) {
            // Getter method — check the return type annotations
            TypeMirror returnType = ( (ExecutableElement) element ).getReturnType();
            JSpecifyNullability result = getNullabilityFromTypeMirror( returnType );
            if ( result != JSpecifyNullability.UNKNOWN ) {
                return result;
            }
        }
        else if ( element instanceof VariableElement ) {
            // Setter parameter or field — check the variable type annotations
            TypeMirror type = element.asType();
            JSpecifyNullability result = getNullabilityFromTypeMirror( type );
            if ( result != JSpecifyNullability.UNKNOWN ) {
                return result;
            }
        }

        // Fallback: check declaration-level annotation mirrors. Some compilers (notably ECJ)
        // surface JSpecify TYPE_USE annotations on the element rather than the type mirror.
        JSpecifyNullability fromElement = getNullabilityFromAnnotationMirrors( element.getAnnotationMirrors() );
        if ( fromElement != JSpecifyNullability.UNKNOWN ) {
            return fromElement;
        }

        // Walk enclosing elements up to the declaring type to honor method-level
        // @NullMarked / @NullUnmarked (e.g. a @NullUnmarked method inside a @NullMarked class
        // must revert unannotated types back to unknown nullability).
        JspecifyNullabilityScope elementScope = resolveElementScope( element );
        if ( elementScope != JspecifyNullabilityScope.UNKNOWN ) {
            return elementScope == JspecifyNullabilityScope.NULL_MARKED
                    ? JSpecifyNullability.NON_NULL : JSpecifyNullability.UNKNOWN;
        }

        // No element-level scope — consult the enclosing bean type's @NullMarked scope.
        if ( resolveNullMarked( element.getEnclosingElement() ) == JspecifyNullabilityScope.NULL_MARKED ) {
            return JSpecifyNullability.NON_NULL;
        }

        return JSpecifyNullability.UNKNOWN;
    }

    /**
     * Walks from {@code element} up to (but not including) its declaring {@link TypeElement},
     * checking for {@code @NullMarked} / {@code @NullUnmarked} on intermediate elements
     * (e.g. the enclosing method of a parameter, or the element itself for a field / getter).
     *
     * @return {@link JspecifyNullabilityScope#NULL_MARKED} when a closer {@code @NullMarked} is found,
     * {@link JspecifyNullabilityScope#NULL_UNMARKED} when a closer {@code @NullUnmarked} is found, or
     * {@link JspecifyNullabilityScope#UNKNOWN} when neither is present before the declaring type is
     * reached (leaving the bean-type scope to decide).
     */
    private static JspecifyNullabilityScope resolveElementScope(Element element) {
        Element current = element;
        while ( current != null && !isTypeElement( current ) ) {
            JspecifyNullabilityScope scope = findScopeAnnotation( current );
            if ( scope != JspecifyNullabilityScope.UNKNOWN ) {
                return scope;
            }
            current = current.getEnclosingElement();
        }
        return JspecifyNullabilityScope.UNKNOWN;
    }

    private static boolean isTypeElement(Element element) {
        ElementKind kind = element.getKind();
        return kind.isClass() || kind.isInterface();
    }

    public JspecifyNullabilityScope getPackageNullabilityScope(PackageElement packageElement) {
        if ( !jSpecifyEnabled ) {
            return JspecifyNullabilityScope.UNKNOWN;
        }
        return resolveNullMarked( packageElement );
    }

    public JspecifyNullabilityScope getParentTypeNullabilityScope(TypeElement typeElement) {
        if ( !jSpecifyEnabled ) {
            return JspecifyNullabilityScope.UNKNOWN;
        }
        return resolveNullMarked( typeElement );
    }

    public JspecifyNullabilityScope getMethodNullabilityScope(ExecutableElement executableElement) {
        if ( !jSpecifyEnabled || executableElement == null ) {
            return JspecifyNullabilityScope.UNKNOWN;
        }
        return resolveElementScope( executableElement );
    }

    public enum JspecifyNullabilityScope {
        UNKNOWN,
        NULL_MARKED,
        NULL_UNMARKED;

        public boolean needsScopeAnnotation(JspecifyNullabilityScope innerScope) {
            switch ( this ) {
                default:
                case UNKNOWN:
                case NULL_UNMARKED:
                    return innerScope ==  NULL_MARKED;
                case NULL_MARKED:
                    return innerScope == NULL_UNMARKED;
            }
        }

        public boolean needsAnnotation(Nullability nullability) {
            if ( nullability.getCause() == Nullability.NullabilityCause.PRIMITIVE
                 || nullability.getCause() == Nullability.NullabilityCause.VOID ) {
                return false;
            }
            switch ( this ) {
                default:
                case UNKNOWN:
                case NULL_UNMARKED:
                    return nullability.isNonNullable();
                case NULL_MARKED:
                    return nullability.isNullable();
            }
        }
    }

    private static JspecifyNullabilityScope findScopeAnnotation(Element element) {
        boolean nullMarked = false;
        boolean nullUnmarked = false;
        for ( AnnotationMirror mirror : element.getAnnotationMirrors() ) {
            Element annotationElement = mirror.getAnnotationType().asElement();
            if ( !( annotationElement instanceof TypeElement ) ) {
                // Defensive: unresolved annotations (e.g. ErrorType during incremental
                // builds) can produce a non-TypeElement. Skip instead of crashing.
                continue;
            }
            String fqn = ( (TypeElement) annotationElement ).getQualifiedName().toString();
            if ( JSpecifyConstants.NULL_MARKED_FQN.equals( fqn ) ) {
                nullMarked = true;
            }
            if ( JSpecifyConstants.NULL_UNMARKED_FQN.equals( fqn ) ) {
                nullUnmarked = true;
            }
        }
        if ( nullMarked != nullUnmarked ) {
            // If only one is set
            return nullMarked ? JspecifyNullabilityScope.NULL_MARKED : JspecifyNullabilityScope.NULL_UNMARKED;
        }
        return JspecifyNullabilityScope.UNKNOWN;
    }

    private static JSpecifyNullability getNullabilityFromTypeMirror(TypeMirror typeMirror) {
        if ( typeMirror == null ) {
            return JSpecifyNullability.UNKNOWN;
        }
        TypeKind kind = typeMirror.getKind();
        if ( kind.isPrimitive() ) {
            return JSpecifyNullability.UNKNOWN;
        }
        if ( kind == TypeKind.ARRAY ) {
            return getNullabilityFromTypeMirror( ((ArrayType) typeMirror).getComponentType() );
        }
        return getNullabilityFromAnnotationMirrors( typeMirror.getAnnotationMirrors() );
    }

    private static JSpecifyNullability getNullabilityFromAnnotationMirrors(
        List<? extends AnnotationMirror> annotationMirrors) {
        boolean nonNull = false;
        boolean nullable = false;
        for ( AnnotationMirror mirror : annotationMirrors ) {
            Element annotationElement = mirror.getAnnotationType().asElement();
            if ( !( annotationElement instanceof TypeElement ) ) {
                // Defensive: during incremental builds the annotation may be an ErrorType
                // whose element is not a TypeElement. Skip instead of failing with a CCE.
                continue;
            }
            String fqn = ( (TypeElement) annotationElement ).getQualifiedName().toString();
            // No direct return, because @NonNull and @Nullable can be used together and cancel each other out
            if ( JSpecifyConstants.NON_NULL_FQN.equals( fqn ) ) {
                nonNull = true;
            }
            if ( JSpecifyConstants.NULLABLE_FQN.equals( fqn ) ) {
                nullable = true;
            }
        }
        if ( nonNull != nullable ) {
            return nonNull ? JSpecifyNullability.NON_NULL : JSpecifyNullability.NULLABLE;
        }
        return JSpecifyNullability.UNKNOWN;
    }

    public static JspecifyNullabilityScope resolveNullMarked(Element typeElement) {
        if ( typeElement == null ) {
            return JspecifyNullabilityScope.UNKNOWN;
        }
        Element current = typeElement;
        while ( current != null ) {
            JspecifyNullabilityScope jspecifyNullabilityScope = findScopeAnnotation( current );
            if (  jspecifyNullabilityScope != JspecifyNullabilityScope.UNKNOWN ) {
                return jspecifyNullabilityScope;
            }
            current = current.getEnclosingElement();
        }
        return JspecifyNullabilityScope.UNKNOWN;
    }
}
