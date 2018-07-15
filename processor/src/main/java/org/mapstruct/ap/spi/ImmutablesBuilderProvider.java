/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.regex.Pattern;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * Builder provider for Immutables. A custom provider is needed because Immutables creates an implementation of an
 * interface and that implementation has the builder. This implementation would try to find the type created by
 * Immutables and would look for the builder in it. Only types annotated with the
 * {@code org.immutables.value.Value.Immutable} are considered for this discovery.
 *
 * @author Filip Hrisafov
 */
public class ImmutablesBuilderProvider extends DefaultBuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );
    private static final String IMMUTABLE_FQN = "org.immutables.value.Value.Immutable";

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement, Elements elements, Types types) {
        Name name = typeElement.getQualifiedName();
        if ( name.length() == 0 || JAVA_JAVAX_PACKAGE.matcher( name ).matches() ) {
            return null;
        }
        TypeElement immutableAnnotation = elements.getTypeElement( IMMUTABLE_FQN );
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
                    // Immutables processor has not run yet. Trigger a postpone to the next round for MapStruct
                    throw new TypeHierarchyErroneousException( typeElement );
                }
            }
        }
        return null;
    }

    protected TypeElement asImmutableElement(TypeElement typeElement, Elements elements) {
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
