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
package org.mapstruct.ap.internal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.spi.BuilderInfo;
import org.mapstruct.ap.spi.BuilderInfo.BuilderInfoBuilder;
import org.mapstruct.ap.spi.BuilderProvider;

/**
 * Default implementation of {@link BuilderProvider} which detect immutable types and their corresponding builders.
 *
 * This implementation amkes the following assumptions:
 *
 * The immutable type will have a static no-arg method that produces a builder instance
 * The builder type will have a single zero-arg method named 'build' that returns the immutable type
 */
public class DefaultBuilderProvider implements BuilderProvider {

    private static final String BUILD_METHOD_NAME = "build";
    private static final String[] IGNORED = new String[] { "java", "com.google", "org.joda" };

    /**
     * Internal cache of known builders
     */
    private final ConcurrentHashMap<String, BuilderInfo> foundBuilders;

    /**
     * Internal cache of types that are constructed using builders
     */
    private final ConcurrentHashMap<String, BuilderInfo> foundBuildees;

    /**
     * Internal cache of types that have been processed and nothing was found.
     */
    private final CopyOnWriteArraySet<String> notFound;

    public DefaultBuilderProvider() {
        this.foundBuilders = new ConcurrentHashMap<String, BuilderInfo>();
        this.foundBuildees = new ConcurrentHashMap<String, BuilderInfo>();
        this.notFound = new CopyOnWriteArraySet<String>();
    }

    @Override
    public BuilderInfo findBuilder(TypeMirror toInspect, Elements elements, Types types) {
        if ( toInspect.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        final TypeElement potentialImmutable = (TypeElement) types.asElement( toInspect );
        final String immutableFQN = potentialImmutable.getQualifiedName().toString();
        if ( foundBuildees.containsKey( immutableFQN ) ) {
            return foundBuildees.get( immutableFQN );
        }

        if ( notFound.contains( immutableFQN ) ) {
            return null;
        }

        for ( String blacklisted : IGNORED ) {
            if ( immutableFQN.startsWith( blacklisted ) ) {
                return null;
            }
        }

        if ( !isImmutable( potentialImmutable, elements ) ) {
            return null;
        }

        for ( BuilderInfoBuilder builder : potentialBuilders( toInspect, elements, types ) ) {
            for ( BuilderInfoBuilder immutable : potentialBuildees( builder.builderType(), elements, types ) ) {
                if ( types.isAssignable( immutable.targetType(), toInspect ) ) {
                    final BuilderInfo foundMapping = BuilderInfo.builder()
                        .merge( builder )
                        .merge( immutable )
                        .targetType( potentialImmutable )
                        .build();
                    foundBuildees.putIfAbsent( immutableFQN, foundMapping );

                    final TypeElement builderType = typeElement( foundMapping.getBuilderType(), types );
                    foundBuilders.putIfAbsent( builderType.getQualifiedName().toString(), foundMapping );
                    return foundMapping;
                }
            }
        }

        notFound.add( immutableFQN );
        return null;
    }

    @Override
    public BuilderInfo findBuildTarget(TypeMirror toInspect, Elements elements, Types types) {
        if ( toInspect.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        TypeElement potentialBuilderType = typeElement( toInspect, types );
        final String builderFQN = potentialBuilderType.getQualifiedName().toString();
        if ( foundBuilders.containsKey( builderFQN ) ) {
            return foundBuilders.get( builderFQN );
        }

        if ( notFound.contains( builderFQN ) ) {
            return null;
        }

        for ( String ignored : IGNORED ) {
            if ( builderFQN.startsWith( ignored ) ) {
                return null;
            }
        }

        for ( BuilderInfoBuilder immutable : potentialBuildees( toInspect, elements, types ) ) {
            for ( BuilderInfoBuilder intermediate : potentialBuilders( immutable.targetType(), elements, types ) ) {
                if ( types.isAssignable( intermediate.builderType(), toInspect ) ) {

                    final BuilderInfo foundMapping = BuilderInfo.builder()
                        .merge( immutable )
                        .merge( intermediate )
                        .builderType( potentialBuilderType )
                        .build();
                    foundBuilders.putIfAbsent( builderFQN, foundMapping );

                    final TypeElement immutableType = typeElement( foundMapping.getFinalType(), types );
                    foundBuildees.putIfAbsent( immutableType.getQualifiedName().toString(), foundMapping );

                    return foundMapping;
                }
            }
        }

        notFound.add( builderFQN );
        return null;
    }

    /**
     *Inspects a {@link TypeMirror} and produces a list of Types that <em>might</em> be builders.
     * <br/>
     * This method is naive - it returns the returnType of any method within {@code builder} thats:
     * <ol>
     *     <li>Accessible</li>
     *     <li>Static</li>
     *     <li>Returns something</li>
     * </ol>
     *
     * Therefore, any results of this method should be cross-referenced to see if the Type also considers itself
     * to be a builder for {@code immutable}
     */
    protected static List<BuilderInfoBuilder> potentialBuilders(TypeMirror immutable, Elements elements, Types types) {
        final TypeElement immutableElement = typeElement( immutable, types );
        final List<BuilderInfoBuilder> potentials = new ArrayList<BuilderInfoBuilder>();
        for ( ExecutableElement method : getAllMethods( immutableElement, elements ) ) {
            if ( isStatic( method ) && hasNoArgs( method ) && isAccessible( method ) && returnsType( method ) ) {
                potentials.add( BuilderInfo.builder()
                    .builderType( typeElement( method.getReturnType(), types ) )
                    .builderCreationMethod( method ) );
            }
        }
        return potentials;
    }

    /**
     * Inspects a {@link TypeMirror} and produces a list of Types that <em>might</em> be built by it.
     * <br/>
     * This method is naive - it produces the return type of any method within {@code builder} thats:
     * <ol>
     *     <li>Accessible</li>
     *     <li>Has no args</li>
     *     <li>Is named 'build'</li>
     *     <li>Returns something</li>
     * </ol>
     *
     * Therefore, any results of this method should be cross-referenced by the return value of the "build" method
     * to see if that type considers {@code builder} a potential builder.
     *
     * @param builder The type we are inspecting to determine if it's a builder
     * @param elements Elements
     * @param types Types
     * @return A list of {@link BuilderInfoBuilder} for each potential build target.
     */
    protected static List<BuilderInfoBuilder> potentialBuildees(TypeMirror builder, Elements elements, Types types) {
        final TypeElement builderElement = typeElement( builder, types );
        final List<BuilderInfoBuilder> potentials = new ArrayList<BuilderInfoBuilder>();

        for ( ExecutableElement method : getAllMethods( builderElement, elements ) ) {
            if ( isAccessible( method ) && !isStatic( method ) && isNamedBuild( method ) && hasNoArgs( method )
                && returnsType( method ) ) {

                final TypeElement potentialImmutable = typeElement( method.getReturnType(), types );
                if ( isImmutable( potentialImmutable, elements ) ) {
                    potentials.add( BuilderInfo.builder()
                        .buildMethod( method )
                        .targetType( potentialImmutable ) );
                }
            }
        }
        return potentials;
    }

    /**
     * Determines if a given type if immutable.  For this implementation, a class is immutable if it does not contain
     * an accessible zero-arg constructor.
     * @param immutableType The type we are checking for immutability
     * @param elements Elements
     * @return True if the provided {@link TypeElement} is immutable
     */
    protected static boolean isImmutable(TypeElement immutableType, Elements elements) {
        for ( Element child : elements.getAllMembers( immutableType ) ) {
            if ( child.getKind() == ElementKind.CONSTRUCTOR ) {
                ExecutableElement constructor = (ExecutableElement) child;
                if ( isAccessible( constructor ) && hasNoArgs( constructor ) ) {
                    // Not considered immutable if it has an accessible no-arg constructor
                    return false;
                }
            }
        }
        return true;
    }

    protected static List<ExecutableElement> getAllMethods(TypeElement typeElement, Elements elements) {
        List<ExecutableElement> methods = new ArrayList<ExecutableElement>();
        for ( Element element : elements.getAllMembers( typeElement ) ) {
            if ( element.getKind() == ElementKind.METHOD && notJavaLangObjectMethod( element ) ) {
                methods.add( (ExecutableElement) element );
            }
        }
        return methods;
    }

    private static boolean notJavaLangObjectMethod(Element element) {
        if ( element.getEnclosingElement() == null ) {
            return true;
        }
        else if ( element.getEnclosingElement().getKind() != ElementKind.CLASS ) {
            return true;
        }
        else {
            final TypeElement enclosingType = (TypeElement) element.getEnclosingElement();
            return !enclosingType.getQualifiedName().contentEquals( "java.lang.Object" );
        }
    }

    private static boolean isAccessible(ExecutableElement method) {
        return !method.getModifiers().contains( Modifier.PRIVATE );
    }

    private static boolean isNamedBuild(ExecutableElement method) {
        return method.getSimpleName().contentEquals( BUILD_METHOD_NAME );
    }

    private static boolean returnsType(ExecutableElement method) {
        return method.getReturnType().getKind() == TypeKind.DECLARED;
    }

    private static boolean isStatic(ExecutableElement method) {
        return method.getModifiers().contains( Modifier.STATIC );
    }

    private static boolean hasNoArgs(ExecutableElement method) {
        return method.getParameters().isEmpty();
    }

    private static TypeElement typeElement(TypeMirror mirror, Types types) {
        return (TypeElement) types.asElement( mirror );
    }

}
