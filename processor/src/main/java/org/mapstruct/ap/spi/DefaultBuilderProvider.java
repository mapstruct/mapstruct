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
package org.mapstruct.ap.spi;

import java.util.List;
import java.util.regex.Pattern;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleElementVisitor6;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

/**
 * Default implementation of {@link BuilderProvider}
 *
 * @author Filip Hrisafov
 */
public class DefaultBuilderProvider implements BuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );

    @Override
    public TypeMirror findBuilder(TypeMirror type, Elements elements, Types types) {
        DeclaredType declaredType = type.accept(
            new SimpleTypeVisitor6<DeclaredType, Void>() {
                @Override
                public DeclaredType visitDeclared(DeclaredType t, Void p) {
                    return t;
                }
            },
            null
        );

        if ( declaredType == null ) {
            return null;
        }

        TypeElement typeElement = declaredType.asElement().accept(
            new SimpleElementVisitor6<TypeElement, Void>() {
                @Override
                public TypeElement visitType(TypeElement e, Void p) {
                    return e;
                }
            },
            null
        );

        return findBuilder( typeElement, elements, types );
    }

    protected TypeMirror findBuilder(TypeElement typeElement, Elements elements, Types types) {
        Name name = typeElement.getQualifiedName();
        if ( name.length() == 0 || JAVA_JAVAX_PACKAGE.matcher( name ).matches() ) {
            return null;
        }
        List<ExecutableElement> methods = ElementFilter.methodsIn( typeElement.getEnclosedElements() );

        for ( ExecutableElement method : methods ) {
            if ( isBuilderMethod( method ) ) {
                return method.getReturnType();
            }
        }

        return findBuilder( typeElement.getSuperclass(), elements, types );
    }

    protected boolean isBuilderMethod(ExecutableElement method) {
        return method.getParameters().isEmpty()
            && method.getSimpleName().toString().equals( "builder" )
            && method.getModifiers().contains( Modifier.PUBLIC )
            && method.getModifiers().contains( Modifier.STATIC );
    }
}
