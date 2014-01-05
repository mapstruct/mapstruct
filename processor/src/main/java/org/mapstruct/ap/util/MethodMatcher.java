/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mapstruct.ap.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

import org.mapstruct.ap.model.Type;
import org.mapstruct.ap.model.source.Method;

/**
 * MethodMatcher
 *
 * $8.4 of the JavaLanguage specification describes a method body as such:
 *
 * MethodDeclaration:
 *  MethodHeader MethodBody
 *
 *  MethodHeader:
 *   MethodModifiers TypeParameters Result MethodDeclarator Throws
 *
 * MethodDeclarator:
 *  Identifier ( FormalParameterList )
 *
 * example <T extends String & Serializable>  T  getResult(? extends T) throws Exception
 *         \-------------------------------/ \-/           \---------/
 *                    TypeParameters        Result        ParameterList
 *
 * Matches a given method with given ParameterList and Result type obeying the
 * constraints in the TypeParameters block.
 *
 * For more info on java-generics:
 * http://www.javacodegeeks.com/2011/04/java-generics-quick-tutorial.html
 * http://www.angelikalanger.com/GenericsFAQ/FAQSections/ParameterizedTypes.html
 *
 * The following situations is not supported / tested:
 * 1) Multiple bounds were the bound itself is again a generic type.
 *
 * @author Sjaak Derksen
 */
public class MethodMatcher {

    private final Type parameter;
    private final Type returnType;
    private final Method candidateMethod;
    private final Types typeUtils;
    private boolean typesMatch = true;
    private final Map<TypeVariable, TypeMirror> genericTypesMap = new HashMap<TypeVariable, TypeMirror>();

    public MethodMatcher(Types typeUtils, Method candidateMethod, Type returnType, Type parameter) {
        this.typeUtils = typeUtils;
        this.candidateMethod = candidateMethod;
        this.parameter = parameter;
        this.returnType = returnType;
    }

    public boolean matches() {
        // check & collect generic types.
        List<? extends VariableElement> candidateParameters = candidateMethod.getExecutable().getParameters();

        if ( candidateParameters.size() != 1 ) {
            typesMatch = false;
        }
        else {
            TypeMatcher parameterMatcher = new TypeMatcher();
            typesMatch = parameterMatcher.visit(
                candidateParameters.iterator().next().asType(),
                parameter.getTypeMirror()
            );
        }

        // check return type
        if ( typesMatch ) {
            TypeMirror candidateReturnType = candidateMethod.getExecutable().getReturnType();
            TypeMatcher returnTypeMatcher = new TypeMatcher();
            typesMatch = returnTypeMatcher.visit( candidateReturnType, returnType.getTypeMirror() );
        }

        // check if all type parameters are indeed mapped
        if ( candidateMethod.getExecutable().getTypeParameters().size() != this.genericTypesMap.size() ) {
            typesMatch = false;
        }
        else {
            // check if all entries are in the bounds
            for (Map.Entry<TypeVariable, TypeMirror> entry : genericTypesMap.entrySet()) {
                if (!isWithinBounds( entry.getValue(), getTypeParamFromCandidate( entry.getKey() ) ) ) {
                    // checks if the found Type is in bounds of the TypeParameters bounds.
                    typesMatch = false;
                }
            }
        }
        return typesMatch;
    }

    public class TypeMatcher extends SimpleTypeVisitor6<Boolean, TypeMirror> {

        public TypeMatcher() {
            super( Boolean.TRUE ); // default value
        }

        @Override
        public Boolean visitPrimitive(PrimitiveType t, TypeMirror p) {
            return typeUtils.isSameType( t, p );
        }

        @Override
        public Boolean visitArray(ArrayType t, TypeMirror p) {

            if ( p.getKind().equals( TypeKind.ARRAY) ) {
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
            if ( p.getKind().equals( TypeKind.DECLARED ) ) {
                DeclaredType t1 = (DeclaredType) p;
                if ( t.asElement().getSimpleName().equals( t1.asElement().getSimpleName() )
                    && t.getTypeArguments().size() == t1.getTypeArguments().size() ) {
                    for ( int i = 0; i < t.getTypeArguments().size(); i++ ) {
                        if (!t.getTypeArguments().get( i ).accept( this, t1.getTypeArguments().get( i ) ))
                        {
                            return Boolean.FALSE;
                        }
                    }
                    return Boolean.TRUE;
                }
                else {
                    return Boolean.FALSE;
                }
            }
            else {
               return Boolean.FALSE;
            }
        }

        @Override
        public Boolean visitTypeVariable(TypeVariable t, TypeMirror p) {
            if ( genericTypesMap.containsKey( t ) ) {
                // when already found, the same mapping should apply
                TypeMirror p1 = genericTypesMap.get( t );
                return typeUtils.isSameType( p, p1 );
            }
            else {
                // check if types are in bound
                if ( typeUtils.isSubtype( t.getLowerBound(), p ) && typeUtils.isSubtype( p, t.getUpperBound() ) ) {
                    genericTypesMap.put( t, p );
                    return Boolean.TRUE;
                }
                else {
                    return Boolean.FALSE;
                }
            }
        }

        @Override
        public Boolean visitWildcard(WildcardType t, TypeMirror p) {

            // check extends bound
            TypeMirror extendsBound = t.getExtendsBound();
            if ( extendsBound != null ) {
                switch ( extendsBound.getKind() ) {
                    case DECLARED:
                        // for example method: String method(? extends String)
                        // isSubType checks range [subtype, type], e.g. isSubtype [Object, String]==true
                        return typeUtils.isSubtype( p, extendsBound );

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
            if ( superBound != null ) {
                switch ( superBound.getKind() ) {
                    case DECLARED:
                        // for example method: String method(? super String)
                        // to check super type, we can simply reverse the argument, but that would initially yield
                        // a result: <type, superType] (so type not included) so we need to check sameType also.
                        return ( typeUtils.isSubtype( superBound, p ) || typeUtils.isSameType( p, superBound ) );

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
                        return ( typeUtils.isSubtype( superBoundAsDeclared, p ) ||
                                typeUtils.isSameType( p, superBoundAsDeclared ) );

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
     * @return matching type parameter
     */
    private TypeParameterElement getTypeParamFromCandidate(TypeMirror t) {
        for ( TypeParameterElement candidateTypeParam : candidateMethod.getExecutable().getTypeParameters() ) {
            if ( candidateTypeParam.asType().equals( t ) ) {
                return candidateTypeParam;
            }
        }
        return null;
    }

    /**
     * checks whether a type t is in bounds of the typeParameter tpe
     *
     * @param t
     * @param tpe
     * @return true if within bounds
     */
    private boolean isWithinBounds(TypeMirror t, TypeParameterElement tpe) {
        List<? extends TypeMirror> bounds = tpe.getBounds();
        if ( t != null && bounds != null ) {
            for ( TypeMirror bound : bounds ) {
                if ( !( bound.getKind().equals( TypeKind.DECLARED ) &&
                        typeUtils.isSubtype( t, bound ) ) ) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
