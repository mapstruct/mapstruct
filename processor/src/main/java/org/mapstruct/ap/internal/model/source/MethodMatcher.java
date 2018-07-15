/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

/**
 * SourceMethodMatcher $8.4 of the JavaLanguage specification describes a method body as such:
 *
 * <pre>
 * SourceMethodDeclaration: SourceMethodHeader SourceMethodBody
 * SourceMethodHeader: SourceMethodModifiers TypeParameters Result SourceMethodDeclarator Throws
 * SourceMethodDeclarator: Identifier ( FormalParameterList )
 *
 * example &lt;T extends String &amp; Serializable&gt;  T   getResult(? extends T) throws Exception
 *         \-------------------------------/ \-/            \---------/
 *               TypeParameters             Result        ParameterList
 * </pre>
 *
 * Matches a given method with given ParameterList and Result type obeying the constraints in the TypeParameters block.
 * <p>
 * For more info on java-generics: http://www.javacodegeeks.com/2011/04/java-generics-quick-tutorial.html
 * http://www.angelikalanger.com/GenericsFAQ/FAQSections/ParameterizedTypes.html
 * <p>
 * The following situations is not supported / tested:
 * <ol>
 * <li>Multiple bounds were the bound itself is again a generic type.</li>
 * </ol>
 *
 * @author Sjaak Derksen
 */
public class MethodMatcher {

    private final SourceMethod candidateMethod;
    private final Types typeUtils;
    private final TypeFactory typeFactory;

    MethodMatcher(Types typeUtils, TypeFactory typeFactory, SourceMethod candidateMethod) {
        this.typeUtils = typeUtils;
        this.candidateMethod = candidateMethod;
        this.typeFactory = typeFactory;
    }

    /**
     * Whether the given source and target types are matched by this matcher's candidate method.
     *
     * @param sourceTypes the source types
     * @param resultType the target type
     * @return {@code true} when both, source type and target types match the signature of this matcher's method;
     *         {@code false} otherwise.
     */
    boolean matches(List<Type> sourceTypes, Type resultType) {

        // check & collect generic types.
        Map<TypeVariable, TypeMirror> genericTypesMap = new HashMap<TypeVariable, TypeMirror>();

        if ( candidateMethod.getParameters().size() == sourceTypes.size() ) {
            int i = 0;
            for ( Parameter candidateParam : candidateMethod.getParameters() ) {
                Type sourceType = sourceTypes.get( i++ );
                if ( sourceType == null
                    || !matchSourceType( sourceType, candidateParam.getType(), genericTypesMap ) ) {
                    return false;
                }
            }
        }
        else {
            return false;
        }

        // check if the method matches the proper result type to construct
        Parameter targetTypeParameter = candidateMethod.getTargetTypeParameter();
        if ( targetTypeParameter != null ) {
            Type returnClassType = typeFactory.classTypeOf( resultType );
            if ( !matchSourceType( returnClassType, targetTypeParameter.getType(), genericTypesMap ) ) {
                return false;
            }
        }

        // check result type
        if ( !matchResultType( resultType, genericTypesMap ) ) {
            return false;
        }

        // check if all type parameters are indeed mapped
        if ( candidateMethod.getExecutable().getTypeParameters().size() != genericTypesMap.size() ) {
            return false;
        }

        // check if all entries are in the bounds
        for ( Map.Entry<TypeVariable, TypeMirror> entry : genericTypesMap.entrySet() ) {
            if ( !isWithinBounds( entry.getValue(), getTypeParamFromCandidate( entry.getKey() ) ) ) {
                // checks if the found Type is in bounds of the TypeParameters bounds.
                return false;
            }
        }
        return true;
    }

    private boolean matchSourceType(Type sourceType,
                                    Type candidateSourceType,
                                    Map<TypeVariable, TypeMirror> genericTypesMap) {

        if ( !isJavaLangObject( candidateSourceType.getTypeMirror() ) ) {
            TypeMatcher parameterMatcher = new TypeMatcher( Assignability.VISITED_ASSIGNABLE_FROM, genericTypesMap );
            if ( !parameterMatcher.visit( candidateSourceType.getTypeMirror(), sourceType.getTypeMirror() ) ) {
                if ( sourceType.isPrimitive() ) {
                    // the candidate source is primitive, so promote to its boxed type and check again (autobox)
                    TypeMirror boxedType = typeUtils.boxedClass( (PrimitiveType) sourceType.getTypeMirror() ).asType();
                    if ( !parameterMatcher.visit( candidateSourceType.getTypeMirror(), boxedType ) ) {
                        return false;
                    }
                }
                else {
                    // NOTE: unboxing is deliberately not considered here. This should be handled via type-conversion
                    // (for NPE safety).
                    return false;
                }
            }
        }
        return true;
    }

    private boolean matchResultType(Type resultType, Map<TypeVariable, TypeMirror> genericTypesMap) {

        Type candidateResultType = candidateMethod.getResultType();

        if ( !isJavaLangObject( candidateResultType.getTypeMirror() ) && !candidateResultType.isVoid() ) {

            final Assignability visitedAssignability;
            if ( candidateMethod.getReturnType().isVoid() ) {
                // for void-methods, the result-type of the candidate needs to be assignable from the given result type
                visitedAssignability = Assignability.VISITED_ASSIGNABLE_FROM;
            }
            else {
                // for non-void methods, the result-type of the candidate needs to be assignable to the given result
                // type
                visitedAssignability = Assignability.VISITED_ASSIGNABLE_TO;
            }

            TypeMatcher returnTypeMatcher = new TypeMatcher( visitedAssignability, genericTypesMap );
            if ( !returnTypeMatcher.visit( candidateResultType.getTypeMirror(), resultType.getTypeMirror() ) ) {
                if ( resultType.isPrimitive() ) {
                    TypeMirror boxedType = typeUtils.boxedClass( (PrimitiveType) resultType.getTypeMirror() ).asType();
                    TypeMatcher boxedReturnTypeMatcher =
                        new TypeMatcher( visitedAssignability, genericTypesMap );

                    if ( !boxedReturnTypeMatcher.visit( candidateResultType.getTypeMirror(), boxedType ) ) {
                        return false;
                    }
                }
                else if ( candidateResultType.getTypeMirror().getKind().isPrimitive() ) {
                    TypeMirror boxedCandidateReturnType =
                        typeUtils.boxedClass( (PrimitiveType) candidateResultType.getTypeMirror() ).asType();
                    TypeMatcher boxedReturnTypeMatcher =
                        new TypeMatcher( visitedAssignability, genericTypesMap );

                    if ( !boxedReturnTypeMatcher.visit( boxedCandidateReturnType, resultType.getTypeMirror() ) ) {
                        return false;
                    }

                }
                else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param type the type
     * @return {@code true}, if the type represents java.lang.Object
     */
    private boolean isJavaLangObject(TypeMirror type) {
        return type.getKind() == TypeKind.DECLARED
            && ( (TypeElement) ( (DeclaredType) type ).asElement() ).getQualifiedName().contentEquals(
                Object.class.getName() );
    }

    private enum Assignability {
        VISITED_ASSIGNABLE_FROM, VISITED_ASSIGNABLE_TO;

        Assignability invert() {
            return this == VISITED_ASSIGNABLE_FROM
                            ? VISITED_ASSIGNABLE_TO
                            : VISITED_ASSIGNABLE_FROM;
        }
    }

    private class TypeMatcher extends SimpleTypeVisitor6<Boolean, TypeMirror> {
        private final Assignability assignability;
        private final Map<TypeVariable, TypeMirror> genericTypesMap;
        private final TypeMatcher inverse;

        TypeMatcher(Assignability assignability, Map<TypeVariable, TypeMirror> genericTypesMap) {
            super( Boolean.FALSE ); // default value
            this.assignability = assignability;
            this.genericTypesMap = genericTypesMap;
            this.inverse = new TypeMatcher( this, genericTypesMap );
        }

        TypeMatcher(TypeMatcher inverse, Map<TypeVariable, TypeMirror> genericTypesMap) {
            super( Boolean.FALSE ); // default value
            this.assignability = inverse.assignability.invert();
            this.genericTypesMap = genericTypesMap;
            this.inverse = inverse;
        }

        @Override
        public Boolean visitPrimitive(PrimitiveType t, TypeMirror p) {
            return typeUtils.isSameType( t, p );
        }

        @Override
        public Boolean visitArray(ArrayType t, TypeMirror p) {

            if ( p.getKind().equals( TypeKind.ARRAY ) ) {
                return t.getComponentType().accept( this, ( (ArrayType) p ).getComponentType() );
            }
            else {
                return Boolean.FALSE;
            }
        }

        @Override
        public Boolean visitDeclared(DeclaredType t, TypeMirror p) {
            // its a match when: 1) same kind of type, name is equals, nr of type args are the same
            // (type args are checked later).
            if ( p.getKind() == TypeKind.DECLARED ) {
                DeclaredType t1 = (DeclaredType) p;
                if ( rawAssignabilityMatches( t, t1 ) ) {
                    if ( t.getTypeArguments().size() == t1.getTypeArguments().size() ) {
                        // compare type var side by side
                        for ( int i = 0; i < t.getTypeArguments().size(); i++ ) {
                            if ( !visit( t.getTypeArguments().get( i ), t1.getTypeArguments().get( i ) ) ) {
                                return Boolean.FALSE;
                            }
                        }
                        return Boolean.TRUE;
                    }
                    else {
                        // return true (e.g. matching Enumeration<E> with an enumeration E)
                        // but do not try to line up raw type arguments with types that do have arguments.
                        return assignability == Assignability.VISITED_ASSIGNABLE_TO ?
                            !t1.getTypeArguments().isEmpty() : !t.getTypeArguments().isEmpty();
                    }
                }
                else {
                    return Boolean.FALSE;
                }
            }
            else if ( p.getKind() == TypeKind.WILDCARD ) {
                return inverse.visit( p, t ); // inverse, as we switch the params
            }
            else {
                return Boolean.FALSE;
            }
        }

        private boolean rawAssignabilityMatches(DeclaredType t1, DeclaredType t2) {
            if ( assignability == Assignability.VISITED_ASSIGNABLE_TO ) {
                return typeUtils.isAssignable( toRawType( t1 ), toRawType( t2 ) );
            }
            else {
                return typeUtils.isAssignable( toRawType( t2 ), toRawType( t1 ) );
            }
        }

        private DeclaredType toRawType(DeclaredType t) {
            return typeUtils.getDeclaredType( (TypeElement) t.asElement() );
        }

        @Override
        public Boolean visitTypeVariable(TypeVariable t, TypeMirror p) {
            if ( genericTypesMap.containsKey( t ) ) {
                // when already found, the same mapping should apply
                TypeMirror p1 = genericTypesMap.get( t );
                return typeUtils.isSubtype( p, p1 );
            }
            else {
                // check if types are in bound
                TypeMirror lowerBound = t.getLowerBound();
                TypeMirror upperBound = t.getUpperBound();
                if ( ( isNullType( lowerBound ) || typeUtils.isSubtype( lowerBound, p ) )
                    && ( isNullType( upperBound ) || typeUtils.isSubtype( p, upperBound ) ) ) {
                    genericTypesMap.put( t, p );
                    return Boolean.TRUE;
                }
                else {
                    return Boolean.FALSE;
                }
            }
        }

        private boolean isNullType(TypeMirror type) {
            return type == null || type.getKind() == TypeKind.NULL;
        }

        @Override
        public Boolean visitWildcard(WildcardType t, TypeMirror p) {

            // check extends bound
            TypeMirror extendsBound = t.getExtendsBound();
            if ( !isNullType( extendsBound ) ) {
                switch ( extendsBound.getKind() ) {
                    case DECLARED:
                        // for example method: String method(? extends String)
                        // isSubType checks range [subtype, type], e.g. isSubtype [Object, String]==true
                        return visit( extendsBound, p );

                    case TYPEVAR:
                        // for example method: <T extends String & Serializable> T method(? extends T)
                        // this can be done the directly by checking: ? extends String & Serializable
                        // this checks the part? <T extends String & Serializable>
                        return isWithinBounds( p, getTypeParamFromCandidate( extendsBound ) );

                    default:
                        // does this situation occur?
                        return Boolean.FALSE;
                }
            }

            // check super bound
            TypeMirror superBound = t.getSuperBound();
            if ( !isNullType( superBound ) ) {
                switch ( superBound.getKind() ) {
                    case DECLARED:
                        // for example method: String method(? super String)
                        // to check super type, we can simply reverse the argument, but that would initially yield
                        // a result: <type, superType] (so type not included) so we need to check sameType also.
                        return typeUtils.isSubtype( superBound, p ) || typeUtils.isSameType( p, superBound );

                    case TYPEVAR:

                        TypeParameterElement typeParameter = getTypeParamFromCandidate( superBound );
                        // for example method: <T extends String & Serializable> T method(? super T)
                        if ( !isWithinBounds( p, typeParameter ) ) {
                            // this checks the part? <T extends String & Serializable>
                            return Boolean.FALSE;
                        }
                        // now, it becomes a bit more hairy. We have the relation (? super T). From T we know that
                        // it is a subclass of String & Serializable. However, The Java Language Secification,
                        // Chapter 4.4, states that a bound is either: 'A type variable-', 'A class-' or 'An
                        // interface-' type followed by further interface types. So we must compare with the first
                        // argument in the Expression String & Serializable & ..., so, in this case String.
                        // to check super type, we can simply reverse the argument, but that would initially yield
                        // a result: <type, superType] (so type not included) so we need to check sameType also.
                        TypeMirror superBoundAsDeclared = typeParameter.getBounds().get( 0 );
                        return ( typeUtils.isSubtype( superBoundAsDeclared, p ) || typeUtils.isSameType(
                            p,
                            superBoundAsDeclared ) );
                    default:
                        // does this situation occur?
                        return Boolean.FALSE;
                }
            }
            return Boolean.TRUE;
        }

    }

    /**
     * Looks through the list of type parameters of the candidate method for a match
     *
     * @param t type parameter to match
     *
     * @return matching type parameter
     */
    private TypeParameterElement getTypeParamFromCandidate(TypeMirror t) {
        for ( TypeParameterElement candidateTypeParam : candidateMethod.getExecutable().getTypeParameters() ) {
            if ( typeUtils.isSameType( candidateTypeParam.asType(), t ) ) {
                return candidateTypeParam;
            }
        }
        return null;
    }

    /**
     * checks whether a type t is in bounds of the typeParameter tpe
     *
     * @return true if within bounds
     */
    private boolean isWithinBounds(TypeMirror t, TypeParameterElement tpe) {
        List<? extends TypeMirror> bounds = tpe != null ? tpe.getBounds() : null;
        if ( t != null && bounds != null ) {
            for ( TypeMirror bound : bounds ) {
                if ( !( bound.getKind() == TypeKind.DECLARED && typeUtils.isSubtype( t, bound ) ) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}

