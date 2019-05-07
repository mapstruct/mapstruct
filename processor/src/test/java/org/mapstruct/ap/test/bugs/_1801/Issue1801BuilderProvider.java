/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._1801;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.ImmutablesBuilderProvider;
import org.mapstruct.ap.spi.MoreThanOneBuilderCreationMethodException;

public class Issue1801BuilderProvider extends ImmutablesBuilderProvider implements BuilderProvider {

    private BuilderInfo findBuilderInfoForImmutables(TypeElement typeElement) {
        return findBuilderInfoFromInnerBuilderClass( typeElement );
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
     * @see org.mapstruct.ap.test.bugs._1801.domain.Item
     *
     * @param typeElement
     * @return
     */
    private BuilderInfo findBuilderInfoFromInnerBuilderClass(TypeElement typeElement) {
        if (shouldIgnore( typeElement )) {
            return null;
        }

        List<TypeElement> types = ElementFilter.typesIn( typeElement.getEnclosedElements() );
        List<BuilderInfo> builderInfo = new ArrayList<>();
        types.stream()
             .filter( innerType -> isPossibleInnerBuilder( innerType, typeElement ) )
             .forEach( innerBuilderClass -> {
                 Collection<ExecutableElement> buildMethods = findBuildMethods( innerBuilderClass, typeElement );
                 if (!buildMethods.isEmpty()) {
                     ElementFilter.constructorsIn( innerBuilderClass.getEnclosedElements() )
                                  .stream()
                                  .filter( constructor -> constructor.getParameters().isEmpty() &&
                                      constructor.getModifiers().contains( Modifier.PUBLIC ) )
                                  .findAny()
                                  .ifPresent( defaultConstructor -> builderInfo.add(
                                      new BuilderInfo.Builder()
                                          .builderCreationMethod( defaultConstructor )
                                          .buildMethod( buildMethods )
                                          .build()
                                  ) );
                 }
             } );

        if (builderInfo.size() == 1) {
            return builderInfo.get( 0 );
        }
        else if (builderInfo.size() > 1) {
            throw new MoreThanOneBuilderCreationMethodException(typeElement.asType(), builderInfo);
        }

        return null;
    }

    private boolean isPossibleInnerBuilder(TypeElement innerClass, TypeElement outerClass) {
        final String packageName = this.elementUtils.getPackageOf( outerClass ).getQualifiedName().toString();
        final Name outerSimpleName = outerClass.getSimpleName();
        final String builderClassName = packageName + ".Immutable" + outerSimpleName + ".Builder";
        return innerClass.getSimpleName().contentEquals( "Builder" ) &&
            getTypeElement( innerClass.getSuperclass() ).getQualifiedName().contentEquals( builderClassName ) &&
            innerClass.getModifiers().contains( Modifier.PUBLIC );
    }

    @Override
    protected BuilderInfo findBuilderInfo(TypeElement typeElement) {
        Name name = typeElement.getQualifiedName();
        if ( name.toString().endsWith( ".Item" ) ) {
            BuilderInfo info = findBuilderInfoForImmutables( typeElement );
            if ( info != null ) {
                return info;
            }
        }

        return super.findBuilderInfo( typeElement );
    }

}
