/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.source;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.NullType;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.type.WildcardType;
import javax.lang.model.util.Types;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
public class SelectionParametersTest {

    private static class TestTypeMirror implements TypeMirror {

        private final String name;

        private TestTypeMirror(String name) {
            this.name = name;
        }

        @Override
        public TypeKind getKind() {
            return null;
        }

        @Override
        public <R, P> R accept(TypeVisitor<R, P> v, P p) {
            return null;
        }

        @Override
        public List<? extends AnnotationMirror> getAnnotationMirrors() {
            return null;
        }

        @Override
        public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
            return null;
        }

        @Override
        public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
            return null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private final Types typeUtils = new Types() {
        @Override
        public Element asElement(TypeMirror t) {
            throw new UnsupportedOperationException( "asElement is not supported" );
        }

        @Override
        public boolean isSameType(TypeMirror t1, TypeMirror t2) {
            return t1.toString().equals( t2.toString() );
        }

        @Override
        public boolean isSubtype(TypeMirror t1, TypeMirror t2) {
            throw new UnsupportedOperationException( "isSubType is not supported" );
        }

        @Override
        public boolean isAssignable(TypeMirror t1, TypeMirror t2) {
            throw new UnsupportedOperationException( "isAssignable is not supported" );
        }

        @Override
        public boolean contains(TypeMirror t1, TypeMirror t2) {
            throw new UnsupportedOperationException( "contains is not supported" );
        }

        @Override
        public boolean isSubsignature(ExecutableType m1, ExecutableType m2) {
            throw new UnsupportedOperationException( "isSubSignature is not supported" );
        }

        @Override
        public List<? extends TypeMirror> directSupertypes(TypeMirror t) {
            throw new UnsupportedOperationException( "directSupertypes is not supported" );
        }

        @Override
        public TypeMirror erasure(TypeMirror t) {
            throw new UnsupportedOperationException( "erasure is not supported" );
        }

        @Override
        public TypeElement boxedClass(PrimitiveType p) {
            throw new UnsupportedOperationException( "boxedClass is not supported" );
        }

        @Override
        public PrimitiveType unboxedType(TypeMirror t) {
            throw new UnsupportedOperationException( "unboxedType is not supported" );
        }

        @Override
        public TypeMirror capture(TypeMirror t) {
            throw new UnsupportedOperationException( "capture is not supported" );
        }

        @Override
        public PrimitiveType getPrimitiveType(TypeKind kind) {
            throw new UnsupportedOperationException( "getPrimitiveType is not supported" );
        }

        @Override
        public NullType getNullType() {
            throw new UnsupportedOperationException( "nullType is not supported" );
        }

        @Override
        public NoType getNoType(TypeKind kind) {
            throw new UnsupportedOperationException( "noType is not supported" );
        }

        @Override
        public ArrayType getArrayType(TypeMirror componentType) {
            throw new UnsupportedOperationException( "getArrayType is not supported" );
        }

        @Override
        public WildcardType getWildcardType(TypeMirror extendsBound, TypeMirror superBound) {
            throw new UnsupportedOperationException( "getWildCardType is not supported" );
        }

        @Override
        public DeclaredType getDeclaredType(TypeElement typeElem, TypeMirror... typeArgs) {
            throw new UnsupportedOperationException( "getDeclaredType is not supported" );
        }

        @Override
        public DeclaredType getDeclaredType(DeclaredType containing, TypeElement typeElem, TypeMirror... typeArgs) {
            throw new UnsupportedOperationException( "getDeclaredType is not supported" );
        }

        @Override
        public TypeMirror asMemberOf(DeclaredType containing, Element element) {
            throw new UnsupportedOperationException( "asMemberOf is not supported" );
        }
    };

    @Test
    public void testGetters() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        assertThat( params.getResultType() ).isSameAs( resultType );
        assertThat( params.getQualifiers() ).hasSameElementsAs( qualifiers );
        assertThat( params.getQualifyingNames() ).hasSameElementsAs( qualifyingNames );
    }

    @Test
    public void testHashCode() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        SelectionParameters params = new SelectionParameters( null, qualifyingNames, resultType, null );

        int expectedHash = 3 * 97 + qualifyingNames.hashCode();
        expectedHash = 97 * expectedHash + "resultType".hashCode();
        assertThat( params.hashCode() ).as( "Expected HashCode" ).isEqualTo( expectedHash );
    }

    @Test
    public void testHashCodeWithAllNulls() {
        SelectionParameters params = new SelectionParameters( null, null, null, null );

        assertThat( params.hashCode() ).as( "All nulls hashCode" ).isEqualTo( 3 * 97 * 97 );
    }

    @Test
    public void testHashCodeWithNullQualifyingNames() {
        TypeMirror resultType = new TestTypeMirror( "someType" );
        SelectionParameters params = new SelectionParameters( null, null, resultType, null );

        assertThat( params.hashCode() )
            .as( "QualifyingNames null hashCode" )
            .isEqualTo( 3 * 97 * 97 + "someType".hashCode() );
    }

    @Test
    public void testHashCodeWithNullResultType() {
        List<String> qualifyingNames = Collections.singletonList( "mapstruct" );
        SelectionParameters params = new SelectionParameters( null, qualifyingNames, null, null );

        assertThat( params.hashCode() )
            .as( "ResultType nulls hashCode" )
            .isEqualTo( ( 3 * 97 + qualifyingNames.hashCode() ) * 97 );
    }

    @Test
    public void testEqualsSameInstance() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        assertThat( params.equals( params ) ).as( "Self equals" ).isTrue();
    }

    @Test
    public void testEqualsWitNull() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        assertThat( params.equals( null ) ).as( "Equals with null" ).isFalse();
    }

    @Test
    public void testEqualsQualifiersOneNull() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );
        SelectionParameters params2 = new SelectionParameters( null, qualifyingNames, resultType, typeUtils );

        assertThat( params.equals( params2 ) ).as( "Second null qualifiers" ).isFalse();
        assertThat( params2.equals( params ) ).as( "First null qualifiers" ).isFalse();
    }

    @Test
    public void testEqualsQualifiersInDifferentOrder() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        List<TypeMirror> qualifiers2 = new ArrayList<TypeMirror>();
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        SelectionParameters params2 = new SelectionParameters( qualifiers2, qualifyingNames, resultType, typeUtils );

        assertThat( params.equals( params2 ) ).as( "Different order for qualifiers" ).isFalse();
        assertThat( params2.equals( params ) ).as( "Different order for qualifiers" ).isFalse();
    }

    @Test
    public void testEqualsQualifyingNamesOneNull() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        List<TypeMirror> qualifiers2 = new ArrayList<TypeMirror>();
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params2 = new SelectionParameters( qualifiers2, null, resultType, typeUtils );

        assertThat( params.equals( params2 ) ).as( "Second null qualifyingNames" ).isFalse();
        assertThat( params2.equals( params ) ).as( "First null qualifyingNames" ).isFalse();
    }

    @Test
    public void testEqualsQualifyingNamesInDifferentOrder() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        List<String> qualifyingNames2 = Arrays.asList( "german", "language" );
        List<TypeMirror> qualifiers2 = new ArrayList<TypeMirror>();
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        SelectionParameters params2 = new SelectionParameters( qualifiers2, qualifyingNames2, resultType, typeUtils );

        assertThat( params.equals( params2 ) ).as( "Different order for qualifyingNames" ).isFalse();
        assertThat( params2.equals( params ) ).as( "Different order for qualifyingNames" ).isFalse();
    }

    @Test
    public void testEqualsResultTypeOneNull() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        List<String> qualifyingNames2 = Arrays.asList( "language", "german" );
        List<TypeMirror> qualifiers2 = new ArrayList<TypeMirror>();
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params2 = new SelectionParameters( qualifiers2, qualifyingNames2, null, typeUtils );

        assertThat( params.equals( params2 ) ).as( "Second null resultType" ).isFalse();
        assertThat( params2.equals( params ) ).as( "First null resultType" ).isFalse();
    }

    @Test
    public void testAllEqual() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        List<String> qualifyingNames2 = Arrays.asList( "language", "german" );
        TypeMirror resultType2 = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers2 = new ArrayList<TypeMirror>();
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params2 = new SelectionParameters( qualifiers2, qualifyingNames2, resultType2, typeUtils );

        assertThat( params.equals( params2 ) ).as( "All equal" ).isTrue();
        assertThat( params2.equals( params ) ).as( "All equal" ).isTrue();
    }

    @Test
    public void testDifferentResultTypes() {
        List<String> qualifyingNames = Arrays.asList( "language", "german" );
        TypeMirror resultType = new TestTypeMirror( "resultType" );
        List<TypeMirror> qualifiers = new ArrayList<TypeMirror>();
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params = new SelectionParameters( qualifiers, qualifyingNames, resultType, typeUtils );

        List<String> qualifyingNames2 = Arrays.asList( "language", "german" );
        TypeMirror resultType2 = new TestTypeMirror( "otherResultType" );
        List<TypeMirror> qualifiers2 = new ArrayList<TypeMirror>();
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeType" ) );
        qualifiers2.add( new TestTypeMirror( "org.mapstruct.test.SomeOtherType" ) );
        SelectionParameters params2 = new SelectionParameters( qualifiers2, qualifyingNames2, resultType2, typeUtils );

        assertThat( params.equals( params2 ) ).as( "Different resultType" ).isFalse();
        assertThat( params2.equals( params ) ).as( "Different resultType" ).isFalse();
    }
}
