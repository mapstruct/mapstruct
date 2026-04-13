/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import org.mapstruct.util.Experimental;

/**
 * Builder provider for Immutables. A custom provider is needed because Immutables creates an implementation of an
 * interface and that implementation has the builder. This implementation would try to find the type created by
 * Immutables and would look for the builder in it. Only types annotated with the
 * {@code org.immutables.value.Value.Immutable} are considered for this discovery.
 * <p>
 * For Java records annotated with {@code org.immutables.value.Value.Builder}, Immutables generates a companion
 * {@code {RecordName}Builder} class (e.g. {@code PersonBuilder} for a record named {@code Person}). This provider
 * detects and uses that builder automatically.
 *
 * @author Filip Hrisafov
 */
@Experimental("The Immutables builder provider might change in a subsequent release")
public class ImmutablesBuilderProvider extends DefaultBuilderProvider {

    private static final Pattern JAVA_JAVAX_PACKAGE = Pattern.compile( "^javax?\\..*" );
    private static final String IMMUTABLE_FQN = "org.immutables.value.Value.Immutable";
    private static final String VALUE_BUILDER_FQN = "org.immutables.value.Value.Builder";
    // Use name() comparison to avoid compile error on Java < 16 where ElementKind.RECORD does not exist
    private static final String RECORD_KIND_NAME = "RECORD";

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

        // Check for Immutables-generated record builder (@Value.Builder on a record)
        BuilderInfo recordBuilderInfo = findBuilderInfoForImmutablesRecord( typeElement );
        if ( recordBuilderInfo != null ) {
            return recordBuilderInfo;
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

    /**
     * Finds builder info for Java records annotated with {@code @Value.Builder}. Immutables generates a companion
     * {@code {RecordName}Builder} class for such records.
     * <p>
     * The companion builder exposes either a static {@code builder()} factory method or a public no-arg constructor
     * as its creation entry point (depending on Immutables configuration). Both are checked in order.
     *
     * @param typeElement the type element to inspect
     * @return builder info if the type is a {@code @Value.Builder}-annotated record with a generated builder,
     *         {@code null} otherwise
     */
    protected BuilderInfo findBuilderInfoForImmutablesRecord(TypeElement typeElement) {
        if ( !RECORD_KIND_NAME.equals( typeElement.getKind().name() ) ) {
            return null;
        }
        TypeElement valueBuilderAnnotation = elementUtils.getTypeElement( VALUE_BUILDER_FQN );
        if ( valueBuilderAnnotation == null ) {
            return null;
        }
        for ( AnnotationMirror annotationMirror : elementUtils.getAllAnnotationMirrors( typeElement ) ) {
            if ( typeUtils.isSameType( annotationMirror.getAnnotationType(), valueBuilderAnnotation.asType() ) ) {
                TypeElement builderElement = asImmutableRecordBuilderElement( typeElement );
                if ( builderElement != null ) {
                    return findBuilderInfoFromCompanionBuilder( builderElement, typeElement );
                }
                else {
                    // Immutables processor has not run yet. Trigger a postpone to the next round for MapStruct
                    throw new TypeHierarchyErroneousException( typeElement );
                }
            }
        }
        return null;
    }

    /**
     * Constructs {@link BuilderInfo} from the Immutables-generated companion builder class for a record.
     * <p>
     * Immutables generates a public no-arg constructor as the builder creation entry point for
     * {@code @Value.Builder} records — analogous to how {@link DefaultBuilderProvider} uses no-arg
     * constructors for inner {@code *Builder} classes.
     */
    private BuilderInfo findBuilderInfoFromCompanionBuilder(TypeElement builderElement, TypeElement recordElement) {
        Collection<ExecutableElement> buildMethods = findBuildMethods( builderElement, recordElement );
        if ( buildMethods.isEmpty() ) {
            return null;
        }

        List<ExecutableElement> constructors = ElementFilter.constructorsIn( builderElement.getEnclosedElements() );
        for ( ExecutableElement constructor : constructors ) {
            if ( constructor.getParameters().isEmpty()
                && constructor.getModifiers().contains( Modifier.PUBLIC ) ) {
                return new BuilderInfo.Builder()
                    .builderCreationMethod( constructor )
                    .buildMethod( buildMethods )
                    .build();
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

    /**
     * Constructs the qualified name of the Immutables-generated builder for a record type and returns the
     * corresponding {@link TypeElement}. The builder class follows the {@code {RecordName}Builder} naming convention.
     *
     * @param typeElement the record type element
     * @return the {@link TypeElement} for the generated builder, or {@code null} if not yet available
     */
    protected TypeElement asImmutableRecordBuilderElement(TypeElement typeElement) {
        Element enclosingElement = typeElement.getEnclosingElement();
        StringBuilder builderQualifiedName = new StringBuilder( typeElement.getQualifiedName().length() + 7 );
        if ( enclosingElement.getKind() == ElementKind.PACKAGE ) {
            builderQualifiedName.append( ( (PackageElement) enclosingElement ).getQualifiedName().toString() );
        }
        else {
            builderQualifiedName.append( ( (TypeElement) enclosingElement ).getQualifiedName().toString() );
        }

        if ( builderQualifiedName.length() > 0 ) {
            builderQualifiedName.append( "." );
        }

        builderQualifiedName.append( typeElement.getSimpleName() ).append( "Builder" );
        return elementUtils.getTypeElement( builderQualifiedName );
    }

}
