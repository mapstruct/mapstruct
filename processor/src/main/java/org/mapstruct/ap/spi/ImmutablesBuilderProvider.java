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

import org.mapstruct.util.Experimental;

/**
 * Builder provider for Immutables. A custom provider is needed because Immutables creates an implementation of an
 * interface and that implementation has the builder. This implementation would try to find the type created by
 * Immutables and would look for the builder in it. Only types annotated with the
 * {@code org.immutables.value.Value.Immutable} are considered for this discovery.
 *
 * @author Filip Hrisafov
 */
@Experimental("The Immutables builder provider might change in a subsequent release")
public class ImmutablesBuilderProvider extends DefaultBuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );
    private static final String IMMUTABLE_FQN = "org.immutables.value.Value.Immutable";

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        if ( name.length() == 0 || JAVA_JAVAX_PACKAGE.matcher( name ).matches() ) {
            return null;
        }

        // First look if there is a builder defined in my own type
        BuilderInfo info = findBuilderInfo( typeElement, false );
        if ( info != null ) {
            return info;
        }

        // Check for a builder in the generated immutable type
        BuilderInfo immutableInfo = findBuilderInfoForImmutables( typeElement );
        if ( immutableInfo != null ) {
            return immutableInfo;
        }

        return super.findBuilderInfo( typeElement.getSuperclass() );
    }

    protected BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement) {
        TypeElement immutableAnnotation = elementUtils.getTypeElement( IMMUTABLE_FQN );
        if ( immutableAnnotation != null ) {
            return findBuilderInfoForImmutables(
                typeElement,
                immutableAnnotation
            );
        }
        return null;
    }

    protected BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement,
                                                       TypeElement immutableAnnotation) {
        for ( AnnotationMirror annotationMirror : elementUtils.getAllAnnotationMirrors( typeElement ) ) {
            if ( typeUtils.isSameType( annotationMirror.getAnnotationType(), immutableAnnotation.asType() ) ) {
                TypeElement immutableElement = asImmutableElement( typeElement );
                if ( immutableElement != null ) {
                    return super.findBuilderInfo( immutableElement, false );
                }
                else {
                    // Immutables processor has not run yet. Trigger a postpone to the next round for MapStruct
                    throw new TypeHierarchyErroneousException( typeElement );
                }
            }
        }
        return null;
    }

    protected TypeElement asImmutableElement(TypeElement typeElement) {
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
        return elementUtils.getTypeElement( builderQualifiedName );
    }

}
