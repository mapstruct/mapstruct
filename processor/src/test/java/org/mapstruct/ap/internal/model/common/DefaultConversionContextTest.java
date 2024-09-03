/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import javax.tools.Diagnostic;

import org.junit.jupiter.api.Test;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.testutil.IssueKey;

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
        Type type = typeWithFQN( ZonedDateTime.class.getCanonicalName() );
        StatefulMessagerMock statefulMessagerMock = new StatefulMessagerMock();
        new DefaultConversionContext(
            null,
            statefulMessagerMock,
            type,
            type,
            new FormattingParameters( "qwertz", null, null, null, null, null )
        );
        assertThat( statefulMessagerMock.getLastKindPrinted() ).isEqualTo( Diagnostic.Kind.ERROR );
    }

    @Test
    public void testNullDateFormatValidation() {
        Type type = typeWithFQN( ZonedDateTime.class.getCanonicalName() );
        StatefulMessagerMock statefulMessagerMock = new StatefulMessagerMock();
        new DefaultConversionContext(
            null,
            statefulMessagerMock,
            type,
            type,
            new FormattingParameters( null, null, null, null, null, null )
        );
        assertThat( statefulMessagerMock.getLastKindPrinted() ).isNull();
    }

    @Test
    public void testUnsupportedType() {
        Type type = typeWithFQN( "java.lang.String" );
        StatefulMessagerMock statefulMessagerMock = new StatefulMessagerMock();
        new DefaultConversionContext( null,
            statefulMessagerMock,
            type,
            type,
            new FormattingParameters( "qwertz", null, null, null, null, null )
        );
        assertThat( statefulMessagerMock.getLastKindPrinted() ).isNull();
    }

    private Type typeWithFQN(String fullQualifiedName) {
        return new Type(
                        null,
                        null,
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
                        false,
            new HashMap<>(  ),
            new HashMap<>(  ),
                        false,
                        false, false
        );
    }

    private static class StatefulMessagerMock implements FormattingMessager {

        private Diagnostic.Kind lastKindPrinted;

        @Override
        public void printMessage(Message msg, Object... arg) {
            throw new UnsupportedOperationException( "Should not be called" );
        }

        @Override
        public void printMessage(Element e, Message msg, Object... arg) {
            throw new UnsupportedOperationException( "Should not be called" );
        }

        @Override
        public void printMessage(Element e, AnnotationMirror a, Message msg, Object... arg) {
            throw new UnsupportedOperationException( "Should not be called" );
        }

        @Override
        public void printMessage(Element e, AnnotationMirror a, AnnotationValue v, Message msg, Object... arg) {
            lastKindPrinted = msg.getDiagnosticKind();
        }

        @Override
        public void note(int level, Message msg, Object... args) {
            throw new UnsupportedOperationException( "Should not be called" );
        }

        public Diagnostic.Kind getLastKindPrinted() {
            return lastKindPrinted;
        }

        @Override
        public boolean isErroneous() {
            return false;
        }
    }
}
