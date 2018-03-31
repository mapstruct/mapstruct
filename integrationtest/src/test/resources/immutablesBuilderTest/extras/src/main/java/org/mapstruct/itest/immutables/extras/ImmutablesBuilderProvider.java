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
package org.mapstruct.itest.immutables.extras;

import java.util.regex.Pattern;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.DefaultBuilderProvider;
import org.mapstruct.ap.spi.TypeHierarchyErroneousException;

/**
 * @author Filip Hrisafov
 */
public class ImmutablesBuilderProvider extends DefaultBuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement, Elements elements, Types types) {
        Name name = typeElement.getQualifiedName();
        if ( name.length() == 0 || JAVA_JAVAX_PACKAGE.matcher( name ).matches() ) {
            return null;
        }
        TypeElement immutableAnnotation = elements.getTypeElement( "org.immutables.value.Value.Immutable" );
        if ( immutableAnnotation != null ) {
            BuilderInfo info = findBuilderInfoForImmutables(
                typeElement,
                immutableAnnotation,
                elements,
                types
            );
            if ( info != null ) {
                return info;
            }
        }

        return super.findBuilderInfo( typeElement, elements, types );
    }

    protected BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement,
        TypeElement immutableAnnotation, Elements elements, Types types) {
        for ( AnnotationMirror annotationMirror : elements.getAllAnnotationMirrors( typeElement ) ) {
            if ( types.isSameType( annotationMirror.getAnnotationType(), immutableAnnotation.asType() ) ) {
                TypeElement immutableElement = asImmutableElement( typeElement, elements );
                if ( immutableElement != null ) {
                    return super.findBuilderInfo( immutableElement, elements, types );
                }
                else {
                    throw new TypeHierarchyErroneousException( typeElement );
                }
            }
        }
        return null;
    }

    private TypeElement asImmutableElement(TypeElement typeElement, Elements elements) {
        Element enclosingElement = typeElement.getEnclosingElement();
        StringBuilder builderQualifiedName = new StringBuilder( typeElement.getQualifiedName().length() + 17 );
        if ( enclosingElement.getKind() == ElementKind.PACKAGE ) {
            builderQualifiedName.append( ( (PackageElement) enclosingElement ).getQualifiedName().toString() );
        }
        else {
            builderQualifiedName.append( ( (TypeElement) enclosingElement ).getQualifiedName().toString() );
        }

        if ( builderQualifiedName.length() > 0 ) {
            builderQualifiedName.append( "." );
        }

        builderQualifiedName.append( "Immutable" ).append( typeElement.getSimpleName() );
        return elements.getTypeElement( builderQualifiedName );
    }
}
