/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model.common;

import org.junit.Test;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.util.JavaTimeConstants;

import javax.annotation.processing.Messager;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Testing DefaultConversionContext for dateFormat
 *
 * @author Timo Eckhardt
 */
@IssueKey( "224" )
public class DefaultConversionContextTest {

    private TypeMirror voidTypeMirror = new TypeMirror() {

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
        public TypeKind getKind() {
            return TypeKind.VOID;
        }

        @Override
        public <R, P> R accept(TypeVisitor<R, P> v, P p) {
            return null;
        }
    };

    @Test
    public void testInvalidDateFormatValidation() {
        Type type = typeWithFQN( JavaTimeConstants.ZONED_DATE_TIME_FQN );
        StatefulMessagerMock statefulMessagerMock = new StatefulMessagerMock();
        new DefaultConversionContext(
                        null, statefulMessagerMock, type, type, "qwertz" );
        assertThat( statefulMessagerMock.getLastKindPrinted() ).isEqualTo( Diagnostic.Kind.ERROR );
    }

    @Test
    public void testNullDateFormatValidation() {
        Type type = typeWithFQN( JavaTimeConstants.ZONED_DATE_TIME_FQN );
        StatefulMessagerMock statefulMessagerMock = new StatefulMessagerMock();
        new DefaultConversionContext(
                        null, statefulMessagerMock, type, type, null );
        assertThat( statefulMessagerMock.getLastKindPrinted() ).isNull();
    }

    @Test
    public void testUnsupportedType() {
        Type type = typeWithFQN( "java.lang.String" );
        StatefulMessagerMock statefulMessagerMock = new StatefulMessagerMock();
        new DefaultConversionContext(
                        null, statefulMessagerMock, type, type, "qwertz" );
        assertThat( statefulMessagerMock.getLastKindPrinted() ).isNull();
    }

    private Type typeWithFQN(String fullQualifiedName) {
        return new Type(
                        null,
                        null,
                        voidTypeMirror,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        fullQualifiedName,
                        false,
                        false,
                        false,
                        false,
                        false,
                        false );
    }

    private static class StatefulMessagerMock implements Messager {

        private Diagnostic.Kind lastKindPrinted;

        @Override
        public void printMessage(Diagnostic.Kind kind, CharSequence msg) {
            lastKindPrinted = kind;
        }

        @Override
        public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e) {
        }

        @Override
        public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a) {
        }

        @Override
        public void printMessage(Diagnostic.Kind kind, CharSequence msg, Element e, AnnotationMirror a,
                                 AnnotationValue v) {
        }

        public Diagnostic.Kind getLastKindPrinted() {
            return lastKindPrinted;
        }

    }
}
