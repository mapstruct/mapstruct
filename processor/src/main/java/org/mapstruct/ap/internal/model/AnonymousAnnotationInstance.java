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
package org.mapstruct.ap.internal.model;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.model.common.ModelElement;
import org.mapstruct.ap.internal.model.common.Type;

public class AnonymousAnnotationInstance extends ModelElement {

    public static class Builder {

        private MappingBuilderContext ctx;
        private AnnotationMirror annotationMirror;

        public Builder ctx(MappingBuilderContext ctx) {
            this.ctx = ctx;
            return this;
        }

        public Builder annotationMirror(AnnotationMirror annotationMirror) {
            this.annotationMirror = annotationMirror;
            return this;
        }

        public AnonymousAnnotationInstance build() {
            List<AnonymousAnnotationMethod> methods = new ArrayList<AnonymousAnnotationMethod>();
            for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                : annotationMirror.getElementValues().entrySet() ) {
                String name = entry.getKey().getSimpleName().toString();
                Type returnType = ctx.getTypeFactory().getType( entry.getKey().getReturnType() );
                String valueRepresentation = entry.getValue().toString();
                methods.add( new AnonymousAnnotationMethod( name, returnType, valueRepresentation ) );
            }
            Type annotationType = ctx.getTypeFactory().getType( annotationMirror.getAnnotationType() );
            Type javaLangClassType = ctx.getTypeFactory().getType( Class.class );
            Type javaLangAnnotationType = ctx.getTypeFactory().getType( Annotation.class );


            return new AnonymousAnnotationInstance(
                annotationType,
                methods,
                javaLangClassType,
                javaLangAnnotationType
            );
        }
    }

    private final Type annotationType;
    private final List<AnonymousAnnotationMethod> annotationMethods;
    private final Type javaLangAnnotationType;
    private final Type javaLangCassType;

    private AnonymousAnnotationInstance(Type annotationType,
                                        List<AnonymousAnnotationMethod> annotationMethods,
                                        Type javaLangCassType, Type javaLangAnnotationType) {
        this.annotationType = annotationType;
        this.annotationMethods = annotationMethods;
        this.javaLangCassType = javaLangCassType;
        this.javaLangAnnotationType = javaLangAnnotationType;
    }

    public List<AnonymousAnnotationMethod> getAnnotationMethods() {
        return annotationMethods;
    }

    public Type getAnnotationType() {
        return annotationType;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> types = new HashSet<Type>();
        types.addAll( annotationType.getImportTypes() );
        for ( AnonymousAnnotationMethod annotationMethod : annotationMethods ) {
            types.addAll( annotationMethod.getImportTypes() );
        }
        types.add( javaLangCassType );
        types.add( javaLangAnnotationType );
        return types;
    }
}
