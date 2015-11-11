/*
 * Copyright 2015 Sjaak Derksen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.eclipse.jdt.internal.compiler.apt.model;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;
import org.eclipse.jdt.internal.compiler.apt.dispatch.BaseProcessingEnvImpl;
import org.eclipse.jdt.internal.compiler.apt.model.DeclaredTypeImpl;
import org.eclipse.jdt.internal.compiler.apt.model.ElementsImpl;
import org.eclipse.jdt.internal.compiler.apt.model.TypesImpl;
import org.eclipse.jdt.internal.compiler.apt.model.WildcardTypeImpl;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;

/**
 *
 * @author Sjaak Derksen
 */
public class EclipseCompilerWorkaroundTypes extends TypesImpl implements Types {

    private final BaseProcessingEnvImpl _env;
    private final TypesImpl types;
    private final ElementsImpl elements;
    private final TypeMirror objectType;


    public EclipseCompilerWorkaroundTypes(TypesImpl typeUtils) {
        super( getEnv( typeUtils ) );
        this._env = getEnv( typeUtils );
        this.types = typeUtils;
        this.elements = (ElementsImpl) _env.getElementUtils();
        this.objectType = elements.getTypeElement( Object.class.getCanonicalName() ).asType();
    }

    private static BaseProcessingEnvImpl getEnv(TypesImpl typeUtils) {
        try {
            Field field = TypesImpl.class.getDeclaredField( "_env" );
            field.setAccessible( true );
            return (BaseProcessingEnvImpl) field.get( typeUtils );
        }
        catch( NoSuchFieldException ex ) {
            throw new RuntimeException();
        }
        catch( SecurityException ex ) {
            throw new RuntimeException();
        }
        catch( IllegalArgumentException ex ) {
            throw new RuntimeException();
        }
        catch( IllegalAccessException ex ) {
            throw new RuntimeException();
        }
    }

    @Override
    public TypeMirror asMemberOf(DeclaredType containing, Element element) {
        if ( asSuper( containing, element.getEnclosingElement() ) == null ) {
            throw new IllegalArgumentException( element + "@" + containing );
        }
        return memberType( containing, element );
    }

    private TypeMirror memberType(TypeMirror t, Element e) {
        return e.getModifiers().contains( Modifier.STATIC ) ? e.asType() : memberType.visit( t, e );
    }

    private SimpleTypeVisitor6<TypeMirror, Element> memberType = new SimpleTypeVisitor6<TypeMirror, Element>() {

        public TypeMirror visitType(TypeMirror t, Element e) {
            return e.asType();
        }

        @Override
        public TypeMirror visitWildcard(WildcardType t, Element e) {
            return memberType( wildUpperBound( t ), e );
        }

        @Override
        public TypeMirror visitDeclared(DeclaredType t, Element e) { // only class in original

            Element owner = e.getEnclosingElement();
            if ( !e.getModifiers().contains( Modifier.STATIC ) && isParameterized( owner.asType() ) ) {
                TypeMirror base = asOuterSuper( t, owner );
                //if t is an intersection type T = CT & I1 & I2 ... & In
                //its supertypes CT, I1, ... In might contain wildcards
                //so we need to go through capture conversion

                DeclaredTypeImpl t_impl = (DeclaredTypeImpl)t;
                base = isCompound( t ) ? types.capture( base ) : base;
                if ( base != null ) {
                    List<TypeMirror> ownerParams =  allParameters(owner.asType());
                    List<TypeMirror> baseParams = allParameters(base);
                    if ( !ownerParams.isEmpty() ) {
                        if ( baseParams.isEmpty() ) {
                            // then base is a raw type
                            return types.erasure( e.asType() );
                        }
                        else {
                            return subst( e.asType(), ownerParams, baseParams );
                        }
                    }
                }
            }
            return e.asType();
        }
    };

    private boolean isParameterized(TypeMirror t) {

        switch( t.getKind() ) {
            case ARRAY:
                return TypeKind.TYPEVAR.equals( ((ArrayType) t).getComponentType().getKind() );
            case DECLARED:
                return ((DeclaredType) t).getTypeArguments().size() > 0;
            default:
                return false;
        }
    }

    private List<TypeMirror> allParameters(TypeMirror t) {

        List<TypeMirror> result = new ArrayList<TypeMirror>();
        switch( t.getKind() ) {
            case ARRAY:
                ArrayType at = (ArrayType)t;
                if ( TypeKind.TYPEVAR.equals( at.getComponentType().getKind() ) ) {
                    result.add( at.getComponentType() );
                }
                break;
            case DECLARED:
                DeclaredType dt = (DeclaredType) t;
                result.addAll( dt.getTypeArguments() );
            default:
        }
        return result;
    }


    private TypeMirror wildUpperBound(WildcardType w) {
        if ( w.getSuperBound() != null ) {
            return w.getSuperBound();
        }
        else if ( w.getExtendsBound() != null ) {
            return w;
        }
        else {
            return elements.getTypeElement( Object.class
                .getCanonicalName() ).asType();
        }

    }

    private TypeMirror asOuterSuper(TypeMirror t, Element e) {

        switch( t.getKind() ) {
            case DECLARED:
                do {
                    TypeMirror s = asSuper( t, e );
                    if ( s != null ) {
                        return s;
                    }
                    t = types.asElement( t ).getEnclosingElement().asType(); // sincerely doubting this
                } while ( TypeKind.DECLARED.equals( t.getKind() ) );
                return null;
            case ARRAY:
                return types.isSubtype( t, e.asType() ) ? e.asType() : null;
            case TYPEVAR:
                return asSuper( t, e );
            case ERROR:
                return t;
            default:
                return null;
        }
    }

    private boolean isCompound(TypeMirror t) {
        switch ( t.getKind() ) {
            case NULL:
            case ERROR:
            case VOID:
            case NONE:
                return false;

            default:
                // how to do this?? The original code is:

            //            return tsym.completer == null
            // Compound types can't have a completer.  Calling
            // flags() will complete the symbol causing the
            // compiler to load classes unnecessarily.  This led
            // to regression 6180021.
            //&& (tsym.flags() & COMPOUND) != 0;

            // however the TypeSymbols are created from their context and the COMPOUND flag is set from that context.
            // somehow we need to derive the type parameters and see if they are compound. But they can be anywwhere
            // on the class, an implemented interface, in front of a method..

                // is there any other way to derive this flag??
        }

        return false;
    }


    /**
     * Return the (most specific) base type of t that starts with the
     * given symbol.  If none exists, return null.
     *
     * @param t a type
     * @param sym a symbol
     */
    private TypeMirror asSuper(TypeMirror t, Element e) {

        /* Some examples:
         *
         * (Enum<E>, Comparable) => Comparable<E>
         * (c.s.s.d.AttributeTree.ValueKind, Enum) => Enum<c.s.s.d.AttributeTree.ValueKind>
         * (c.s.s.t.ExpressionTree, c.s.s.t.Tree) => c.s.s.t.Tree
         * (j.u.List<capture#160 of ? extends c.s.s.d.DocTree>, Iterable) =>
         *     Iterable<capture#160 of ? extends c.s.s.d.DocTree>
         */
        if ( types.isSameType( e.asType(), objectType ) )  {
            //optimization
            return objectType;
        }
        return asSuper.visit( t, e );
    }
    // where
     private SimpleTypeVisitor6<TypeMirror, Element> asSuper = new SimpleTypeVisitor6<TypeMirror, Element>() {

            public TypeMirror visitType(TypeMirror t, Element e) {
                return null;
            }

            @Override
            public TypeMirror visitDeclared(DeclaredType t, Element e) {
                if ( types.isSameType( t, e.asType() ) ) {
                    return t;
                }

                TypeMirror st = supertype ( t );
                if ( TypeKind.DECLARED.equals( st.getKind() ) || TypeKind.TYPEVAR.equals( st.getKind() ) ) {

                    TypeMirror x = asSuper(st, e);
                    if (x != null)
                        return x;
                }
                // both annotations and plain interfaces are interfaces
                if ( ElementKind.INTERFACE.equals( e.getKind() ) || ElementKind.ANNOTATION_TYPE.equals( e.getKind() ) ) {
                    for ( List<TypeMirror> l = interfaces( t ); !l.isEmpty(); l = tail( l ) ) {
                        if ( !TypeKind.ERROR.equals(  head ( l ).getKind() ) ) {
                            TypeMirror x = asSuper( head ( l ), e );
                            if ( x != null ) {
                                return x;
                            }
                        }
                    }
                }
                return null;
            }

            @Override
            public TypeMirror visitArray(ArrayType t, Element e) {
                return types.isSubtype(t, e.asType()) ? e.asType() : null;
            }


            @Override
            public TypeMirror visitTypeVariable(TypeVariable t, Element e) {
                if (types.isSameType( t, e.asType() ) ) {
                    return t;
                }
                else
                    return asSuper(t.getUpperBound(), e);
            }

            @Override
            public TypeMirror visitError(ErrorType t, Element e) {
                return t;
            }
        };


    private TypeMirror subst(TypeMirror t, List<TypeMirror> from, List<TypeMirror> to) {
        return new Subst( from, to ).subst( t );
    }

     private class Subst extends UnaryVisitor<TypeMirror> {
        List<TypeMirror> from;
        List<TypeMirror> to;

        public Subst(List<TypeMirror> from, List<TypeMirror> to) {
            int fromLength = from.size();
            int toLength = to.size();
            while (fromLength > toLength) {
                fromLength--;
                from =  tail ( from );
            }
            while (fromLength < toLength) {
                toLength--;
                to = tail ( to );
            }
            this.from = from;
            this.to = to;
        }

         TypeMirror subst(TypeMirror t) {
             if ( tail( from ) == null ) {
                 return t;
             }
             else {
                 return visit( t );
             }
         }

        List<? extends TypeMirror> subst(List<? extends TypeMirror> ts) {
            if ( tail (from ) == null)
                return ts;
            boolean wild = false;
            if (!ts.isEmpty() && !from.isEmpty()) {
                TypeMirror head1 = subst( head( ts ) );
                List<? extends TypeMirror> tail1 = tail( ts );
                if ( !head1.equals( head( ts ) ) || !tail1.equals( tail( ts ) ) )
                    return join( head1,  tail1 );
            }
            return ts;
        }

        public TypeMirror visitType(TypeMirror t, Void ignored) {
            return t;
        }

         @Override
         public TypeMirror visitExecutable(ExecutableType t, Void ignored) {
             List<? extends TypeMirror> argtypes = subst( t.getParameterTypes() );
             TypeMirror restype = subst( t.getReturnType() );
             List<? extends TypeMirror> thrown = subst( t.getThrownTypes() );
             if ( argtypes.equals( t.getParameterTypes() )
                 && restype.equals( t.getReturnType() )
                 && thrown.equals( t.getThrownTypes() ) ) {
                 return t;
             }
             else {
                 ExecutableTypeImpl et = (ExecutableTypeImpl)t;
                 MethodBinding b = (MethodBinding)et._binding;
                 //return new MethodType(argtypes, restype, thrown, t.tsym);

                 MethodBinding nb = new MethodBinding(b.modifiers, b.selector, b.returnType, b.parameters, b.thrownExceptions, b.declaringClass);

                 return _env.getFactory().newTypeMirror( nb );
             }
         }

         private List<TypeBinding> toBinding( List<? extends TypeMirror> in) {
             
         }

        @Override
        public TypeMirror visitTypeVariable(TypeVariable t, Void ignored) {
            for (List<TypeMirror> l_from = this.from, l_to = this.to;
                 !l_from.isEmpty();
                 l_from = tail (l_from ), l_to = tail ( l_to ) ) {
                if (t == head ( l_from ) ) {
                    return head ( l_to ); // withTypeVar is mostly this (appart from AnnotatedType and wildcard type)
                }
            }
            return t;
        }

        @Override
        public TypeMirror visitUnknown(TypeMirror t, Void p) {
            //do nothing - we should not replace inside undet variables
            return t;
        }

        @Override
        public TypeMirror visitDeclared(DeclaredType t, Void ignored) {
            List<? extends TypeMirror> typarams = t.getTypeArguments();
            List<? extends TypeMirror> typarams1 = subst( typarams );
            TypeMirror outer = t.getEnclosingType();
            TypeMirror outer1 = subst( outer );
            if ( typarams1 == typarams && outer1 == outer ) {
                return t;
            }
            else {
                return null;//new ClassType( outer1, typarams1, t.asElement() );
            }
        }

         @Override
         public TypeMirror visitWildcard(WildcardType t, Void ignored) {
             TypeMirror bound = t.getExtendsBound() != null ? t.getExtendsBound() : t.getSuperBound();
             if ( bound != null ) {
                 bound = subst( bound );
             }
             if ( types.isSameType( bound, t.getExtendsBound() != null ? t.getExtendsBound() : t.getSuperBound() ) ) {
                 return t;
             }
             else {
                 if ( t.getExtendsBound() != null && (((WildcardType) bound).getExtendsBound() != null) ) {
                     bound = wildUpperBound( (WildcardType)bound );
                 }
                 return  null; //new WildcardType( bound, t.kind, syms.boundClass, t.bound );
             }
         }

        @Override
        public TypeMirror visitArray(ArrayType t, Void ignored) {
            TypeMirror elemtype = subst( t.getComponentType() );
            if ( types.isSameType( elemtype, t.getComponentType() ) )
                return t;
            else
                return null; //new ArrayType(elemtype, t.tsym);
        }

//        @Override
//        TODO
//        public Type visitForAll(ForAll t, Void ignored) {
//            if (Type.containsAny(to, t.tvars)) {
//                //perform alpha-renaming of free-variables in 't'
//                //if 'to' types contain variables that are free in 't'
//                List<Type> freevars = newInstances(t.tvars);
//                t = new ForAll(freevars,
//                        Types.this.subst(t.qtype, t.tvars, freevars));
//            }
//            List<Type> tvars1 = substBounds(t.tvars, from, to);
//            Type qtype1 = subst(t.qtype);
//            if (tvars1 == t.tvars && qtype1 == t.qtype) {
//                return t;
//            } else if (tvars1 == t.tvars) {
//                return new ForAll(tvars1, qtype1);
//            } else {
//                return new ForAll(tvars1, Types.this.subst(qtype1, t.tvars, tvars1));
//            }
//        }


        @Override
        public TypeMirror visitError(ErrorType t, Void ignored) {
            return t;
        }
    }
    // <editor-fold defaultstate="collapsed" desc="supertype">
    public TypeMirror supertype(TypeMirror t) {
        return null; //supertype.visit(t);
    }
    // where
//        private UnaryVisitor<TypeMirror> supertype = new UnaryVisitor<TypeMirror>() {
//
//            public TypeMirror visitType(TypeMirror t, Void ignored) {
//                // A note on wildcards: there is no good way to
//                // determine a supertype for a super bounded wildcard.
//                return Type.noType;
//            }
//
//            @Override
//            public TypeMirror visitDeclared(DeclaredType t, Void ignored) {
//                if ( t.supertype_field == null ) {
//                    TypeMirror supertype = ((ClassSymbol) t.tsym).getSuperclass();
//                    // An interface has no superclass; its supertype is Object.
//                    if ( t.isInterface() ) {
//                        supertype = ((ClassType) t.tsym.type).supertype_field;
//                    }
//                    if ( t.supertype_field == null ) {
//                        List<TypeMirror> actuals = classBound( t ).allparams();
//                        List<TypeMirror> formals = t.tsym.type.allparams();
//                        if ( t.hasErasedSupertypes() ) {
//                            t.supertype_field = erasureRecursive( supertype );
//                        }
//                        else if ( formals.nonEmpty() ) {
//                            t.supertype_field = subst( supertype, formals, actuals );
//                        }
//                        else {
//                            t.supertype_field = supertype;
//                        }
//                    }
//                }
//                return t.supertype_field;
//            }
//
//            /**
//             * The supertype is always a class type. If the type
//             * variable's bounds start with a class type, this is also
//             * the supertype.  Otherwise, the supertype is
//             * java.lang.Object.
//             */
//            @Override
//            public Type visitTypeVar(TypeVar t, Void ignored) {
//                if (t.bound.hasTag(TYPEVAR) ||
//                    (!t.bound.isCompound() && !t.bound.isInterface())) {
//                    return t.bound;
//                } else {
//                    return supertype(t.bound);
//                }
//            }
//
//            @Override
//            public Type visitArrayType(ArrayType t, Void ignored) {
//                if (t.elemtype.isPrimitive() || isSameType(t.elemtype, syms.objectType))
//                    return arraySuperType();
//                else
//                    return new ArrayType(supertype(t.elemtype), t.tsym);
//            }
//
//            @Override
//            public Type visitErrorType(ErrorType t, Void ignored) {
//                return Type.noType;
//            }
//        };
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="interfaces">
    /**
     * Return the interfaces implemented by this class.
     */
    private List<TypeMirror> interfaces(TypeMirror t) {
        return interfaces.visit(t);
    }
    // where
        private UnaryVisitor<List<TypeMirror>> interfaces = new UnaryVisitor<List<TypeMirror>>() {

            public List<TypeMirror> visitType(TypeMirror t, Void ignored) {
                return new ArrayList();
            }

//            @Override
//            public List<TypeMirror> visitDeclared(DeclaredType t, Void ignored) {
//                if (t.interfaces_field == null) {
//
//                    List<? extends TypeMirror> interfaces = ((TypeElement) t.asElement()).getInterfaces();
//                    if (t.interfaces_field == null) {
//                        // If t.interfaces_field is null, then t must
//                        // be a parameterized type (not to be confused
//                        // with a generic type declaration).
//                        // Terminology:
//                        //    Parameterized type: List<String>
//                        //    Generic type declaration: class List<E> { ... }
//                        // So t corresponds to List<String> and
//                        // t.tsym.type corresponds to List<E>.
//                        // The reason t must be parameterized type is
//                        // that completion will happen as a side
//                        // effect of calling
//                        // ClassSymbol.getInterfaces.  Since
//                        // t.interfaces_field is null after
//                        // completion, we can assume that t is not the
//                        // type of a class/interface declaration.
//                        Assert.check(t != t.tsym.type, t);
//                        List<Type> actuals = t.allparams();
//                        List<Type> formals = t.tsym.type.allparams();
//                        if (t.hasErasedSupertypes()) {
//                            t.interfaces_field = erasureRecursive(interfaces);
//                        } else if (formals.nonEmpty()) {
//                            t.interfaces_field = subst(interfaces, formals, actuals);
//                        }
//                        else {
//                            t.interfaces_field = interfaces;
//                        }
//                    }
//                }
//                return t.interfaces_field;
//            }

            @Override
            public List<TypeMirror> visitTypeVariable(TypeVariable t, Void ignored) {
//                if (t.bound.isCompound())
//                    return interfaces(t.bound);
//
//                if (t.bound.isInterface())
//                    return List.of(t.bound);
//
//                return List.nil();
                return null;
            }
        };


    public static abstract class UnaryVisitor<R> extends SimpleTypeVisitor6<R, Void> {

    }


    private List<TypeMirror> join(TypeMirror head, List<? extends TypeMirror> tail) {
        List<TypeMirror> result = new ArrayList( tail );
        java.util.Collections.reverse( result );
        result.add( head );
        java.util.Collections.reverse( result );
        return result;
    }

    private <T> T head(List<T> in) {
        return in.get( 0 );
    }

    private <T> List<T> tail(List<T> in) {
        return in.subList( 1, in.size() );
    }



}
