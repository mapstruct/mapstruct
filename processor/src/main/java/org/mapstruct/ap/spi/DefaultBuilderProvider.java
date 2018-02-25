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
 * Default implementation of {@link BuilderProvider}.
 *
 * The default builder provider considers all public static parameterless methods of a {@link TypeMirror}
 * as potential builder creation methods. For each potential builder creation method checks in the return type
 * of the method if there exists a method that returns the initial {@link TypeMirror} if such a combination is found
 * the {@link BuilderInfo} is created with those 2 methods.
 * Example:
 * <pre><code>
 * public class Person {
 *
 *     private final String firstName;
 *     private final String lastName;
 *
 *     private Person(String firstName, String lastName) {
 *         this.firstName = firstName;
 *         this.lastName = lastName;
 *     }
 *
 *     //getters
 *
 *     public static Builder builder() {
 *         return new Builder();
 *     }

 *     public static class Builder {
 *
 *         private String firstName;
 *         private String lastName;
 *
 *         private Builder() {}
 *
 *         //fluent setters
 *
 *         public Person create() {
 *             return new Person( firstName, lastName );
 *         }
 *     }
 * }
 * </code></pre>
 *
 * In the example above, when searching for a builder for the {@code Person} type. The {@code Person#builder} method
 * would be a builder creation candidate. Then the return type of {@code Person#builder}, {@code Builder}, is
 * investigated for a parameterless method that returns {@code Person}. When {@code Builder#create} is found
 * the {@link BuilderInfo} is created with the {@code Person#builder} as a builder creation method and
 * {@code Builder#create} as a build method.
 * <p>
 * <b>IMPORTANT</b>: Types from the {@code java} and {@code javax} packages are excluded from inspection
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

    /**
     * Find the {@link TypeElement} for the given {@link TypeMirror}.
     *
     * @param type for which the {@link TypeElement} needs to be found/
     *
     * @return the type element or {@code null} if the {@link TypeMirror} is not a {@link DeclaredType}
     * and the declared type element is not {@link TypeElement}
     *
     * @throws TypeHierarchyErroneousException if the {@link TypeMirror} is of kind {@link TypeKind#ERROR}
     */
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

    /**
     * Find the {@link BuilderInfo} for the given {@code typeElement}.
     * <p>
     * The default implementation iterates over all the methods in {@code typeElement} and uses
     * {@link DefaultBuilderProvider#isPossibleBuilderCreationMethod(ExecutableElement, TypeElement, Types)} and
     * {@link DefaultBuilderProvider#findBuildMethod(TypeElement, TypeElement, Types)} to create the
     * {@link BuilderInfo}.
     * <p>
     * The default implementation uses {@link DefaultBuilderProvider#shouldIgnore(TypeElement)} to check if the
     * {@code typeElement} should be ignored.
     *
     * @param typeElement the type element for which a builder searched
     * @param elements the util elements that can be used for operating on the type element
     * @param types the util types that can be used for operation on {@link TypeMirror}(s)
     *
     * @return the {@link BuilderInfo} or {@code null} if no builder was found for the type
     */
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

    /**
     * Checks if the {@code method} is a possible builder creation method.
     * <p>
     * The default implementation considers a method as a possible creation method if the following is satisfied:
     * <ul>
     * <li>The method has no parameters</li>
     * <li>It is a {@code public static} method</li>
     * <li>The return type of the {@code method} is not the same as the {@code typeElement}</li>
     * <li></li>
     * </ul>
     *
     * @param method The method that needs to be checked
     * @param typeElement the enclosing element of the method, i.e. the type in which the method is located in
     * @param types the util types that can be used for operations on {@link TypeMirror}(s)
     *
     * @return {@code true} if the {@code method} is a possible builder creation method, {@code false} otherwise
     */
    protected boolean isPossibleBuilderCreationMethod(ExecutableElement method, TypeElement typeElement, Types types) {
        return method.getParameters().isEmpty()
            && method.getModifiers().contains( Modifier.PUBLIC )
            && method.getModifiers().contains( Modifier.STATIC )
            && !types.isSameType( method.getReturnType(), typeElement.asType() );
    }

    /**
     * Searches for a build method for {@code typeElement} within the {@code builderElement}.
     * <p>
     * The default implementation iterates over each method in {@code builderElement} and uses
     * {@link DefaultBuilderProvider#isBuildMethod(ExecutableElement, TypeElement, Types)} to check if the method is a
     * build method for {@code typeElement}.
     * <p>
     * The default implementation uses {@link DefaultBuilderProvider#shouldIgnore(TypeElement)} to check if the
     * {@code builderElement} should be ignored, i.e. not checked for build elements.
     *
     * @param builderElement the element for the builder
     * @param typeElement the element for the type that is being built
     * @param types the util types tat can be used for operations on {@link TypeMirror}(s)
     *
     * @return the build method for the {@code typeElement} if it exists, or {@code null} if it does not
     */
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

    /**
     * Checks if the {@code buildMethod} is a method that creates {@code typeElement}.
     * <p>
     * The default implementation considers a method to be a build method if the following is satisfied:
     * <ul>
     * <li>The method has no parameters</li>
     * <li>The method is public</li>
     * <li>The return type of method is assignable to the {@code typeElement}</li>
     * </ul>
     *
     * @param buildMethod the method that should be checked
     * @param typeElement the type element that needs to be built
     * @param types the util types that can be used for operations on {@link TypeMirror}(s)
     *
     * @return {@code true} if the {@code buildMethod} is a build method for {@code typeElement}, {@code false}
     * otherwise
     */
    protected boolean isBuildMethod(ExecutableElement buildMethod, TypeElement typeElement, Types types) {
        return buildMethod.getParameters().isEmpty() &&
            buildMethod.getModifiers().contains( Modifier.PUBLIC )
            && types.isAssignable( buildMethod.getReturnType(), typeElement.asType() );
    }

    /**
     * Whether the {@code typeElement} should be ignored, i.e. not used in inspection.
     * <p>
     * The default implementations ignores {@code null} elements and elements that belong to the {@code java} and
     * {@code javax} packages
     * @param typeElement that needs to be checked
     * @return {@code true} if the element should be ignored, {@code false} otherwise
     */
    protected boolean shouldIgnore(TypeElement typeElement) {
        return typeElement == null || JAVA_JAVAX_PACKAGE.matcher( typeElement.getQualifiedName() ).matches();
    }
}
