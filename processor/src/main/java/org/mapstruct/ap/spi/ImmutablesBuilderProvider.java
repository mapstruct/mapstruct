/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Map;
import java.util.regex.Pattern;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
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

    private static final String DEFAULT_PREFIX = "Immutable";
    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );
    private static final String IMMUTABLE_VALUE_FQN = "org.immutables.value.Value.Immutable";
    private static final String IMMUTABLE_STYLE_FQN = "org.immutables.value.Value.Style";
    private static final String IMMUTABLE_STYLE_TYPE_FIELD = "typeImmutable";

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        if ( name.length() == 0 || JAVA_JAVAX_PACKAGE.matcher( name ).matches() ) {
            return null;
        }
        TypeElement immutableAnnotation = elementUtils.getTypeElement( IMMUTABLE_VALUE_FQN );
        if ( immutableAnnotation != null ) {
            BuilderInfo info = findBuilderInfoForImmutables( typeElement, immutableAnnotation );
            if ( info != null ) {
                return info;
            }
        }

        return super.findBuilderInfo( typeElement );
    }

    protected BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement,
                                                       TypeElement immutableValueAnnotationTypeElement) {
        for ( AnnotationMirror annotationMirror : elementUtils.getAllAnnotationMirrors( typeElement ) ) {
            if ( typeUtils.isSameType(
                annotationMirror.getAnnotationType(),
                immutableValueAnnotationTypeElement.asType()
            ) ) {
                TypeElement immutableElement = asImmutableElement( typeElement );
                if ( immutableElement != null ) {
                    return super.findBuilderInfo( immutableElement );
                }
                else {
                    // Immutables processor has not run yet. Trigger postpone
                    // to the next round for MapStruct
                    throw new TypeHierarchyErroneousException( typeElement );
                }
            }
        }
        return null;
    }

    private AnnotationMirror getAnnotation(TypeElement typeElement, TypeElement annotation) {
        for ( AnnotationMirror annotationMirror : elementUtils.getAllAnnotationMirrors( typeElement ) ) {
            if ( typeUtils.isSameType( annotationMirror.getAnnotationType(), annotation.asType() ) ) {
                return annotationMirror;
            }
        }
        return null;
    }

    private String getImmutableClassName(TypeElement typeElement) {
        TypeElement immutableStyleTypeElement = elementUtils.getTypeElement( IMMUTABLE_STYLE_FQN );
        // might not be annotated with @Value.Style
        if ( immutableStyleTypeElement != null ) {
            AnnotationMirror immutableStyleAnnotation = getAnnotation( typeElement, immutableStyleTypeElement );
            if ( immutableStyleAnnotation != null ) {
                // extract typeImmutable field value
                for ( Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry :
                    immutableStyleAnnotation.getElementValues()
                        .entrySet() ) {
                    if ( IMMUTABLE_STYLE_TYPE_FIELD.equals( entry.getKey().getSimpleName().toString() ) ) {
                        String typeImmutableValue = (String) entry.getValue().getValue();
                        return typeImmutableValue.replace( "*", typeElement.getSimpleName() );
                    }
                }
            }
        }

        return DEFAULT_PREFIX + typeElement.getSimpleName();
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

        String className = getImmutableClassName( typeElement );
        builderQualifiedName.append( className );
        return elementUtils.getTypeElement( builderQualifiedName );
    }

}
