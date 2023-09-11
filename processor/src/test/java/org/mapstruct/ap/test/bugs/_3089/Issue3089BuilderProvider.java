/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._3089;

import java.util.List;
import java.util.Objects;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;

import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;

/**
 * @author Oliver Erhart
 */
public class Issue3089BuilderProvider extends ImmutablesBuilderProvider implements BuilderProvider {

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        if ( name.toString().endsWith( ".Item" ) ) {
            BuilderInfo info = findBuilderInfoFromInnerBuilderClass( typeElement );
            if ( info != null ) {
                return info;
            }
        }
        return super.findBuilderInfo( typeElement );
    }

    /**
     * Looks for inner builder class in the Immutable interface / abstract class.
     *
     * The inner builder class should be be declared with the following line
     *
     * <pre>
     *     public static Builder() extends ImmutableItem.Builder { }
     * </pre>
     *
     * The Immutable instance should be created with the following line
     *
     * <pre>
     *     new Item.Builder().withId("123").build();
     * </pre>
     *
     * @see org.mapstruct.ap.test.bugs._3089.domain.Item
     *
     * @param typeElement
     * @return
     */
    private BuilderInfo findBuilderInfoFromInnerBuilderClass(TypeElement typeElement) {
        if (shouldIgnore( typeElement )) {
            return null;
        }

        List<TypeElement> innerTypes = ElementFilter.typesIn( typeElement.getEnclosedElements() );
        ExecutableElement defaultConstructor = innerTypes.stream()
                                                         .filter( this::isBuilderCandidate )
                                                         .map( this::getEmptyArgPublicConstructor )
                                                         .filter( Objects::nonNull )
                                                         .findAny()
                                                         .orElse( null );

        if ( defaultConstructor != null ) {
            return new BuilderInfo.Builder()
                .builderCreationMethod( defaultConstructor )
                .buildMethod( findBuildMethods( (TypeElement) defaultConstructor.getEnclosingElement(), typeElement ) )
                .build();
        }
        return null;
    }

    private boolean isBuilderCandidate(TypeElement innerType ) {
        TypeElement outerType = (TypeElement) innerType.getEnclosingElement();
        String packageName = this.elementUtils.getPackageOf( outerType ).getQualifiedName().toString();
        Name outerSimpleName = outerType.getSimpleName();
        String builderClassName = packageName + ".Immutable" + outerSimpleName + ".Builder";
        return innerType.getSimpleName().contentEquals( "Builder" )
            && getTypeElement( innerType.getSuperclass() ).getQualifiedName().contentEquals( builderClassName )
            && innerType.getModifiers().contains( Modifier.PUBLIC );
    }

    private ExecutableElement getEmptyArgPublicConstructor(TypeElement builderType) {
        return ElementFilter.constructorsIn( builderType.getEnclosedElements() ).stream()
                     .filter( c -> c.getParameters().isEmpty() )
                     .filter( c -> c.getModifiers().contains( Modifier.PUBLIC ) )
                     .findAny()
            .orElse( null );
    }
}
