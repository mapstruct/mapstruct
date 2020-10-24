/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.TypeUtils;

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
    private final TypeUtils typeUtils;
    private final TypeFactory typeFactory;

    MethodMatcher(TypeUtils typeUtils, TypeFactory typeFactory, SourceMethod candidateMethod) {
        this.typeUtils = typeUtils;
        this.candidateMethod = candidateMethod;
        this.typeFactory = typeFactory;
    }

    /**
     * Whether the given source and target types are matched by this matcher's candidate method.
     *
     * @param sourceTypes the source types
     * @param targetType the target type
     * @return {@code true} when both, source type and target types match the signature of this matcher's method;
     *         {@code false} otherwise.
     */
    boolean matches(List<Type> sourceTypes, Type targetType) {

        GenericAnalyser analyser =
            new GenericAnalyser( typeFactory, typeUtils, candidateMethod, sourceTypes, targetType );
        if ( !analyser.lineUp() ) {
            return false;
        }

        for ( int i = 0; i < sourceTypes.size(); i++ ) {
            Type candidateSourceParType = analyser.candidateParTypes.get( i );
            if ( !( sourceTypes.get( i ).isAssignableTo( candidateSourceParType )
                && !isPrimitiveToObject( sourceTypes.get( i ), candidateSourceParType ) ) ) {
                return false;
            }
        }

        // TODO: TargetType checking should not be part of method selection, it should be in checking the annotation
        // (the relation target / target type, target type being a class)

        if ( !analyser.candidateReturnType.isVoid() ) {
            if ( !( analyser.candidateReturnType.isAssignableTo( targetType ) ) ){
                return false;
            }
        }

        return true;
    }

    /**
     * Primitive to Object (Boxed Type) should be handled by a type conversion rather than a direct mapping
     * Direct mapping runs the risk of null pointer exceptions: e.g. in the assignment of Integer to int, Integer
     * can be null.
     *
     * @param type the type to be assigned
     * @param isAssignableTo the type to which @param type should be assignable to
     * @return true if isAssignable is a primitive type and type is an object
     */
    private boolean isPrimitiveToObject( Type type, Type isAssignableTo ) {
        if ( isAssignableTo.isPrimitive() ) {
            return !type.isPrimitive();
        }
        return false;
    }

    private static class GenericAnalyser {

        private TypeFactory typeFactory;
        private TypeUtils typeUtils;
        private Method candidateMethod;
        private List<Type> sourceTypes;
        private Type targetType;

        GenericAnalyser(TypeFactory typeFactory, TypeUtils typeUtils, Method candidateMethod,
                               List<Type> sourceTypes, Type targetType) {
            this.typeFactory = typeFactory;
            this.typeUtils = typeUtils;
            this.candidateMethod = candidateMethod;
            this.sourceTypes = sourceTypes;
            this.targetType = targetType;
        }

        Type candidateReturnType = null;
        List<Type> candidateParTypes;
        Integer positionMappingTargetType = null;

        private boolean lineUp() {

            if ( candidateMethod.getParameters().size() != sourceTypes.size() ) {
                return false;
            }

            if ( !candidateMethod.getTypeParameters().isEmpty() ) {

                this.candidateParTypes = new ArrayList<>();

                // Per generic method parameter the associated type variable candidates
                Map<Type, TypeVarCandidate> methodParCandidates = new HashMap<>();

                // Get candidates
                boolean success = getCandidates( methodParCandidates );
                if ( !success ) {
                    return false;
                }

                // Check type bounds
                boolean withinBounds = candidatesWithinBounds( methodParCandidates );
                if ( !withinBounds ) {
                    return false;
                }

                // Represent result as map.
                Map<Type, Type> resolvedPairs = new HashMap<>();
                for ( TypeVarCandidate candidate : methodParCandidates.values() ) {
                    for ( Type.ResolvedPair pair : candidate.pairs) {
                        resolvedPairs.put( pair.getParameter(), pair.getMatch() );
                    }
                }

                // Resolve parameters and return type by using the found candidates
                int nrOfMethodPars = candidateMethod.getParameters().size();
                for ( int i = 0; i < nrOfMethodPars; i++ ) {
                    Type candidateType = resolve( candidateMethod.getParameters().get( i ).getType(), resolvedPairs );
                    if ( candidateType == null ) {
                        return false;
                    }
                    this.candidateParTypes.add( candidateType );

                }
                if ( !candidateMethod.getReturnType().isVoid() ) {
                    this.candidateReturnType = resolve( candidateMethod.getReturnType(), resolvedPairs );
                    if ( this.candidateReturnType == null ) {
                        return false;
                    }
                }
                else {
                    this.candidateReturnType = candidateMethod.getReturnType();
                }
            }
            else {
                this.candidateParTypes = candidateMethod.getParameters().stream()
                    .map( Parameter::getType )
                    .collect( Collectors.toList() );
                this.candidateReturnType = candidateMethod.getReturnType();
            }
            return true;
        }

        /**
         * {@code <T, U extends Number> T map( U in ) }
         *
         * Resolves all method generic parameter candidates
         *
         * @param methodParCandidates Map, keyed by the method generic parameter (T, U extends Number), with their
         *                            respective candidates
         *
         * @return false no match or conflict has been found           *
         */
        boolean getCandidates( Map<Type, TypeVarCandidate> methodParCandidates) {

            int nrOfMethodPars = candidateMethod.getParameters().size();
            Type returnType = candidateMethod.getReturnType();

            for ( int i = 0; i < nrOfMethodPars; i++ ) {
                Type sourceType = sourceTypes.get( i );
                Parameter par = candidateMethod.getParameters().get( i );
                Type parType = par.getType();
                if ( par.isMappingTarget() ) {
                    positionMappingTargetType = i;
                }
                boolean success = getCandidates( parType, sourceType, methodParCandidates );
                if ( !success ) {
                    return false;
                }
            }
            if ( !returnType.isVoid() ) {
                boolean success = getCandidates( returnType, targetType, methodParCandidates );
                if ( !success ) {
                    return false;
                }
            }
            return true;
        }

        /**
         * @param aCandidateMethodType parameter type or return type from candidate method
         * @param matchingType source type / target type to match
         * @param candidates Map, keyed by the method generic parameter, with the candidates
         *
         * @return false no match or conflict has been found
         */
        boolean getCandidates(Type aCandidateMethodType, Type matchingType, Map<Type, TypeVarCandidate> candidates ) {

            if ( !( aCandidateMethodType.isTypeVar()
                || aCandidateMethodType.isArrayTypeVar()
                || aCandidateMethodType.isWildCardBoundByTypeVar()
                || hasGenericTypeParameters( aCandidateMethodType ) ) ) {
                // the typeFromCandidateMethod is not a generic (parameterized) type
                return true;
            }

            for ( Type mthdParType : candidateMethod.getTypeParameters() ) {

                // typeFromCandidateMethod itself is a generic type, e.g. <T> String method( T par );
                // typeFromCandidateMethod is a generic arrayType e.g. <T> String method( T[] par );
                // typeFromCandidateMethod is embedded in another type e.g. <T> String method( Callable<T> par );
                // typeFromCandidateMethod is a wildcard, bounded by a typeVar
                // e.g. <T> String method( List<? extends T> in )

                Type.ResolvedPair resolved = mthdParType.resolveParameterToType( matchingType, aCandidateMethodType );
                if ( resolved == null ) {
                    // cannot find a candidate type, but should have since the typeFromCandidateMethod had parameters
                    // to be resolved
                    return !hasGenericTypeParameters( aCandidateMethodType );
                }

                // resolved something at this point, a candidate can be fetched or created
                TypeVarCandidate typeVarCandidate;
                if ( candidates.containsKey( mthdParType )  ) {
                    typeVarCandidate = candidates.get( mthdParType );
                }
                else {
                    // add a new type
                     typeVarCandidate = new TypeVarCandidate( );
                     candidates.put( mthdParType, typeVarCandidate );
                }

                // check what we've resolved
                if ( resolved.getParameter().isTypeVar() ) {
                    // it might be already set, but we just checked if its an equivalent type
                    if ( typeVarCandidate.match == null ) {
                        typeVarCandidate.match = resolved.getMatch();
                        if ( typeVarCandidate.match == null) {
                            return false;
                        }
                        typeVarCandidate.pairs.add( resolved );
                    }
                    else if ( !areEquivalent( resolved.getMatch(), typeVarCandidate.match ) ) {
                        // type has been resolved twice, but with a different candidate (conflict)
                        return false;
                    }

                }
                else if ( resolved.getParameter().isArrayTypeVar()
                    && resolved.getParameter().getComponentType().isAssignableTo( mthdParType ) ) {
                    // e.g. <T extends Number> T map( List<T[]> in ), the match for T should be assignable
                    // to the parameter T extends Number
                    typeVarCandidate.pairs.add( resolved );
                }
                else if ( resolved.getParameter().isWildCardBoundByTypeVar()
                    && resolved.getParameter().getTypeBound().isAssignableTo( mthdParType ) )  {
                    // e.g. <T extends Number> T map( List<? super T> in ), the match for ? super T should be assignable
                    // to the parameter T extends Number
                    typeVarCandidate.pairs.add( resolved );
                }
                else {
                    // none of the above
                    return false;
                }
            }
            return true;
        }


        /**
         * Checks whether all found candidates are within the bounds of the method type var. For instance
         * @<code><T, U extends Callable<T> U map( T in )</code>. Note that only the relation between the
         * match for U and Callable are checked. Not the correct parameter.
         *
         * @param methodParCandidates
         *
         * @return true when all within bounds.
         */
        private boolean candidatesWithinBounds(Map<Type, TypeVarCandidate> methodParCandidates ) {
            for ( Map.Entry<Type, TypeVarCandidate> entry : methodParCandidates.entrySet() ) {
                Type bound = entry.getKey().getTypeBound();
                if ( bound != null ) {
                    for ( Type.ResolvedPair pair : entry.getValue().pairs ) {
                        if ( entry.getKey().hasUpperBound() ) {
                            if ( !pair.getMatch().asRawType().isAssignableTo( bound.asRawType() ) ) {
                                return false;
                            }
                        }
                        else {
                            // lower bound
                            if ( !bound.asRawType().isAssignableTo( pair.getMatch().asRawType() ) ) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }

        private boolean hasGenericTypeParameters(Type typeFromCandidateMethod) {
            for ( Type typeParam : typeFromCandidateMethod.getTypeParameters() ) {
                if ( typeParam.isTypeVar() || typeParam.isWildCardBoundByTypeVar() || typeParam.isArrayTypeVar() ) {
                    return true;
                }
                else {
                    if ( hasGenericTypeParameters( typeParam ) ) {
                        return true;
                    }
                }
            }
            return false;
        }

        private Type resolve( Type typeFromCandidateMethod, Map<Type, Type> pairs ) {
            if ( typeFromCandidateMethod.isTypeVar() || typeFromCandidateMethod.isArrayTypeVar() ) {
                return pairs.get( typeFromCandidateMethod );
            }
            else if ( hasGenericTypeParameters( typeFromCandidateMethod ) ) {
                TypeMirror[] typeArgs = new TypeMirror[ typeFromCandidateMethod.getTypeParameters().size() ];
                for ( int i = 0; i < typeFromCandidateMethod.getTypeParameters().size(); i++ ) {
                    Type typeFromCandidateMethodTypeParameter = typeFromCandidateMethod.getTypeParameters().get( i );
                    if ( hasGenericTypeParameters( typeFromCandidateMethodTypeParameter ) ) {
                        // nested type var, lets resolve some more (recur)
                        Type matchingType = resolve( typeFromCandidateMethodTypeParameter, pairs );
                        if ( matchingType == null ) {
                            // something went wrong
                            return null;
                        }
                        typeArgs[i] = matchingType.getTypeMirror();
                    }
                    else if ( typeFromCandidateMethodTypeParameter.isWildCardBoundByTypeVar()
                        || typeFromCandidateMethodTypeParameter.isTypeVar()
                        || typeFromCandidateMethodTypeParameter.isArrayTypeVar()
                    ) {
                        Type matchingType = pairs.get( typeFromCandidateMethodTypeParameter );
                        if ( matchingType == null ) {
                            // something went wrong
                            return null;
                        }
                        typeArgs[i] = matchingType.getTypeMirror();
                    }
                    else {
                        // it is not a type var (e.g. Map<String, T> ), String is not a type var
                        typeArgs[i] = typeFromCandidateMethodTypeParameter.getTypeMirror();
                    }
                }
                DeclaredType typeArg = typeUtils.getDeclaredType( typeFromCandidateMethod.getTypeElement(), typeArgs );
                return typeFactory.getType( typeArg );
            }
            else {
                // its not a type var or generic parameterized (e.g. just a plain type)
                return typeFromCandidateMethod;
            }
        }

        boolean areEquivalent( Type a, Type b ) {
            if ( a == null || b == null ) {
                return false;
            }
            return a.getBoxedEquivalent().equals( b.getBoxedEquivalent() );
        }
    }

    private static class TypeVarCandidate {

        private Type match;
        private List<Type.ResolvedPair> pairs = new ArrayList<>();

    }

}

