/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.common;

import org.junit.Test;
import org.mapstruct.ap.internal.util.JavaTimeConstants;
import org.mapstruct.ap.internal.util.JodaTimeConstants;
import org.mapstruct.ap.testutil.IssueKey;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Tests for {@link org.mapstruct.ap.internal.model.common.DateFormatValidatorFactory}.
 *
 * @author Timo Eckhardt
 */
@IssueKey( "224" )
public class DateFormatValidatorFactoryTest {

    private static final String JAVA_LANG_STRING = "java.lang.String";

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
    public void testUnsupportedTypes() {
        Type sourceType = typeWithFQN( JAVA_LANG_STRING );
        Type targetType = typeWithFQN( JAVA_LANG_STRING );
        DateFormatValidator dateFormatValidator = DateFormatValidatorFactory.forTypes( sourceType, targetType );
        assertThat( dateFormatValidator.validate( "XXXX" ).isValid() ).isTrue();
    }

    @Test
    public void testJavaUtilDateValidator() {

        Type sourceType = typeWithFQN( "java.util.Date" );
        Type targetType = typeWithFQN( JAVA_LANG_STRING );

        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );
    }

    @Test
    public void testJodaTimeValidator() {

        Type targetType = typeWithFQN( JAVA_LANG_STRING );

        Type sourceType = typeWithFQN( JodaTimeConstants.DATE_TIME_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );

        sourceType = typeWithFQN( JodaTimeConstants.LOCAL_DATE_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );

        sourceType = typeWithFQN( JodaTimeConstants.LOCAL_DATE_TIME_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );

        sourceType = typeWithFQN( JodaTimeConstants.LOCAL_TIME_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );
    }

    @Test
    public void testJavaTimeValidator() {

        Type targetType = typeWithFQN( JAVA_LANG_STRING );

        Type sourceType = typeWithFQN( JavaTimeConstants.ZONED_DATE_TIME_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );

        sourceType = typeWithFQN( JavaTimeConstants.LOCAL_DATE_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );

        sourceType = typeWithFQN( JavaTimeConstants.LOCAL_DATE_TIME_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );

        sourceType = typeWithFQN( JavaTimeConstants.LOCAL_TIME_FQN );
        assertInvalidDateFormat( sourceType, targetType );
        assertInvalidDateFormat( targetType, sourceType );
        assertValidDateFormat( sourceType, targetType );
        assertValidDateFormat( targetType, sourceType );
    }

    private void assertInvalidDateFormat(Type sourceType, Type targetType) {
        DateFormatValidator dateFormatValidator = DateFormatValidatorFactory.forTypes( sourceType, targetType );
        DateFormatValidationResult result = dateFormatValidator.validate( "qwertz" );
        assertThat( result.isValid() ).isFalse();
    }

    private void assertValidDateFormat(Type sourceType, Type targetType) {
        DateFormatValidator dateFormatValidator = DateFormatValidatorFactory.forTypes( sourceType, targetType );
        DateFormatValidationResult result = dateFormatValidator.validate( "YYYY" );
        assertThat( result.isValid() ).isTrue();
    }

    private Type typeWithFQN(String fullQualifiedName) {
        return new Type(
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
                        false );
    }

}
