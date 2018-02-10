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
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
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
    public BuilderInfo findBuilderInfo(TypeMirror type, Elements elements, Types types) {
        TypeElement typeElement = getTypeElement( type );
        if ( typeElement == null ) {
            return null;
        }

        return findBuilderInfo( typeElement, elements, types );
    }

    protected TypeElement getTypeElement(TypeMirror type) {
        if ( type.getKind() == TypeKind.ERROR ) {
            throw new TypeHierarchyErroneousException( type );
        }
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

        return declaredType.asElement().accept(
            new SimpleElementVisitor6<TypeElement, Void>() {
                @Override
                public TypeElement visitType(TypeElement e, Void p) {
                    return e;
                }
            },
            null
        );
    }

    protected BuilderInfo findBuilderInfo(TypeElement typeElement, Elements elements, Types types) {
        if ( shouldIgnore( typeElement ) ) {
            return null;
        }

        List<ExecutableElement> methods = ElementFilter.methodsIn( typeElement.getEnclosedElements() );
        for ( ExecutableElement method : methods ) {
            if ( isPossibleBuilderCreationMethod( method, typeElement, types ) ) {
                TypeElement builderElement = getTypeElement( method.getReturnType() );
                ExecutableElement buildMethod = findBuildMethod( builderElement, typeElement, types );
                if ( buildMethod != null ) {
                    return new BuilderInfo.Builder()
                        .builderCreationMethod( method )
                        .buildMethod( buildMethod )
                        .build();
                }
            }
        }
        return findBuilderInfo( typeElement.getSuperclass(), elements, types );
    }

    protected boolean isPossibleBuilderCreationMethod(ExecutableElement method, TypeElement typeElement, Types types) {
        return method.getParameters().isEmpty()
            && method.getModifiers().contains( Modifier.PUBLIC )
            && method.getModifiers().contains( Modifier.STATIC )
            && !types.isSameType( method.getReturnType(), typeElement.asType() );
    }

    protected ExecutableElement findBuildMethod(TypeElement builderElement, TypeElement typeElement, Types types) {
        if ( shouldIgnore( builderElement ) ) {
            return null;
        }

        List<ExecutableElement> builderMethods = ElementFilter.methodsIn( builderElement.getEnclosedElements() );
        for ( ExecutableElement buildMethod : builderMethods ) {
            if ( isBuildMethod( buildMethod, typeElement, types ) ) {
                return buildMethod;
            }
        }

        return findBuildMethod(
            getTypeElement( builderElement.getSuperclass() ),
            typeElement,
            types
        );
    }

    protected boolean isBuildMethod(ExecutableElement buildMethod, TypeElement typeElement,
        Types types) {
        return buildMethod.getParameters().isEmpty() &&
            buildMethod.getModifiers().contains( Modifier.PUBLIC )
            && types.isAssignable( buildMethod.getReturnType(), typeElement.asType() );
    }

    protected boolean shouldIgnore(TypeElement typeElement) {
        return typeElement == null || JAVA_JAVAX_PACKAGE.matcher( typeElement.getQualifiedName() ).matches();
    }
}
