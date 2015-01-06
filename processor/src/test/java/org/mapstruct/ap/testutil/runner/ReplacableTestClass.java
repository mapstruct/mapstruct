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
package org.mapstruct.ap.testutil.runner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;

import org.junit.runners.model.FrameworkField;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.TestClass;

/**
 * A {@link TestClass} where the wrapped Class can be replaced
 *
 * @author Andreas Gudian
 */
class ReplacableTestClass extends TestClass {
    private TestClass delegate;

    /**
     * @see TestClass#TestClass(Class)
     */
    ReplacableTestClass(Class<?> clazz) {
        super( clazz );
    }

    /**
     * @param clazz the new class
     */
    void replaceClass(Class<?> clazz) {
        delegate = new TestClass( clazz );
    }

    @Override
    public List<FrameworkMethod> getAnnotatedMethods() {
        if ( null == delegate ) {
            return super.getAnnotatedMethods();
        }
        else {
            return delegate.getAnnotatedMethods();
        }
    }

    @Override
    public List<FrameworkMethod> getAnnotatedMethods(Class<? extends Annotation> annotationClass) {
        if ( null == delegate ) {
            return super.getAnnotatedMethods( annotationClass );
        }
        else {
            return delegate.getAnnotatedMethods( annotationClass );
        }
    }

    @Override
    public List<FrameworkField> getAnnotatedFields() {
        if ( null == delegate ) {
            return super.getAnnotatedFields();
        }
        else {
            return delegate.getAnnotatedFields();
        }
    }

    @Override
    public List<FrameworkField> getAnnotatedFields(Class<? extends Annotation> annotationClass) {
        if ( null == delegate ) {
            return super.getAnnotatedFields( annotationClass );
        }
        else {
            return delegate.getAnnotatedFields( annotationClass );
        }
    }

    @Override
    public Class<?> getJavaClass() {
        if ( null == delegate ) {
            return super.getJavaClass();
        }
        else {
            return delegate.getJavaClass();
        }
    }

    @Override
    public String getName() {
        if ( null == delegate ) {
            return super.getName();
        }
        else {
            return delegate.getName();
        }
    }

    @Override
    public Constructor<?> getOnlyConstructor() {
        if ( null == delegate ) {
            return super.getOnlyConstructor();
        }
        else {
            return delegate.getOnlyConstructor();
        }
    }

    @Override
    public Annotation[] getAnnotations() {
        if ( null == delegate ) {
            return super.getAnnotations();
        }
        else {
            return delegate.getAnnotations();
        }
    }

    @Override
    public <T extends Annotation> T getAnnotation(Class<T> annotationType) {
        if ( null == delegate ) {
            return super.getAnnotation( annotationType );
        }
        else {
            return delegate.getAnnotation( annotationType );
        }
    }

    @Override
    public <T> List<T> getAnnotatedFieldValues(Object test, Class<? extends Annotation> annotationClass,
                                               Class<T> valueClass) {
        if ( null == delegate ) {
            return super.getAnnotatedFieldValues( test, annotationClass, valueClass );
        }
        else {
            return delegate.getAnnotatedFieldValues( test, annotationClass, valueClass );
        }
    }

    @Override
    public <T> List<T> getAnnotatedMethodValues(Object test, Class<? extends Annotation> annotationClass,
                                                Class<T> valueClass) {
        if ( null == delegate ) {
            return super.getAnnotatedMethodValues( test, annotationClass, valueClass );
        }
        else {
            return delegate.getAnnotatedMethodValues( test, annotationClass, valueClass );
        }
    }

    @Override
    public String toString() {
        if ( null == delegate ) {
            return super.toString();
        }
        else {
            return delegate.toString();
        }
    }

    @Override
    public boolean isPublic() {
        if ( null == delegate ) {
            return super.isPublic();
        }
        else {
            return delegate.isPublic();
        }
    }

    @Override
    public boolean isANonStaticInnerClass() {
        if ( null == delegate ) {
            return super.isANonStaticInnerClass();
        }
        else {
            return delegate.isANonStaticInnerClass();
        }
    }

    @Override
    public int hashCode() {
        if ( null == delegate ) {
            return super.hashCode();
        }
        else {
            return delegate.hashCode();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if ( null == delegate ) {
            return super.equals( obj );
        }
        else {
            return delegate.equals( obj );
        }
    }
}
