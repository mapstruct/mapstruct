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

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import org.mapstruct.ap.spi.BuilderProvider;
import org.mapstruct.ap.spi.BuilderMapping;

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
    private static final String[] BLACKLISTED = new String[] { "java", "com.google", "org.joda" };

    // Internal cache of known builders
    private static final ConcurrentHashMap<String, BuilderMapping> BUILDERS = newBuilderMappingCache();

    // Internal cache of types that are constructed using builders
    private static final ConcurrentHashMap<String, BuilderMapping> BUILDEES = newBuilderMappingCache();

    @Override
    public BuilderMapping findBuilder(TypeMirror toInspect, Elements elements, Types types) {
        if ( toInspect.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        final TypeElement potentialImmutable = typeElement( toInspect );
        final String immutableFQN = potentialImmutable.getQualifiedName().toString();
        if ( BUILDEES.containsKey( immutableFQN ) ) {
            return BUILDEES.get( immutableFQN );
        }

        for ( String blacklisted : BLACKLISTED ) {
            if ( immutableFQN.startsWith( blacklisted ) ) {
                return null;
            }
        }

        if ( !isImmutable( potentialImmutable ) ) {
            return null;
        }

        for ( BuilderMapping.Builder potentialBuilder : findPotentialBuilders( toInspect ) ) {
            for ( BuilderMapping.Builder immutable : findPotentialBuildTarget( potentialBuilder.builderType() ) ) {
                if ( types.isAssignable( immutable.targetType(), toInspect ) ) {
                    final BuilderMapping foundMapping = BuilderMapping.builder()
                        .merge( potentialBuilder )
                        .merge( immutable )
                        .targetType( potentialImmutable )
                        .build();
                    BUILDEES.putIfAbsent( immutableFQN, foundMapping );

                    final TypeElement builderType = typeElement( foundMapping.getBuilderType() );
                    BUILDERS.putIfAbsent( builderType.getQualifiedName().toString(), foundMapping );
                }
            }
        }

        return null;
    }

    @Override
    public BuilderMapping findBuildTarget(TypeMirror toInspect, Elements elements, Types types) {
        if ( toInspect.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        TypeElement potentialBuilderType = typeElement( toInspect );
        final String builderFQN = potentialBuilderType.getQualifiedName().toString();
        if ( BUILDERS.containsKey( builderFQN ) ) {
            return BUILDERS.get( builderFQN );
        }

        for ( String blacklisted : BLACKLISTED ) {
            if ( potentialBuilderType.getQualifiedName().toString().startsWith( blacklisted ) ) {
                return null;
            }
        }

        for ( BuilderMapping.Builder potentialBuildee : findPotentialBuildTarget( toInspect ) ) {
            for ( BuilderMapping.Builder intermediate : findPotentialBuilders( potentialBuildee.targetType() ) ) {
                if ( types.isAssignable( intermediate.builderType(), toInspect ) ) {

                    final BuilderMapping foundMapping = BuilderMapping.builder()
                        .merge( potentialBuildee )
                        .merge( intermediate )
                        .builderType( potentialBuilderType )
                        .build();
                    BUILDERS.putIfAbsent( builderFQN, foundMapping );

                    final TypeElement immutableType = typeElement( foundMapping.getFinalType() );
                    BUILDEES.putIfAbsent( immutableType.getQualifiedName().toString(), foundMapping );

                    return foundMapping;
                }
            }
        }
        return null;
    }

    /**
     * Given an immutable type, finds all potential builder classes by:
     *
     * a) looking at public static no-args methods
     * b) looking at inner classes
     *
     * These are compared with the results of {@link #findPotentialBuildTarget(TypeMirror)} to find matches.
     */
    private static List<BuilderMapping.Builder> findPotentialBuilders(TypeMirror immutableType) {
        final TypeElement immutableElement = typeElement( immutableType );
        final List<BuilderMapping.Builder> potentials = new ArrayList<BuilderMapping.Builder>();
        for ( Element child : immutableElement.getEnclosedElements() ) {
            if ( child.getKind() == ElementKind.METHOD ) {
                ExecutableElement method = (ExecutableElement) child;
                if ( isStatic( method ) && !hasArgs( method ) && isPublic( method ) && returnsDeclaredType( method ) ) {
                    potentials.add( BuilderMapping.builder()
                        .builderType( typeElement( method.getReturnType() ) )
                        .builderCreationMethod( method ) );
                }
            }
        }
        return potentials;
    }

    private static List<BuilderMapping.Builder> findPotentialBuildTarget(TypeMirror builderType) {
        final TypeElement builderElement = typeElement( builderType );
        assert builderType != null : "Builder should not be null";
        final List<BuilderMapping.Builder> potentials = new ArrayList<BuilderMapping.Builder>();

        for ( Element builderChild : builderElement.getEnclosedElements() ) {
            if ( builderChild.getKind() == ElementKind.METHOD ) {
                ExecutableElement buildMethod = (ExecutableElement) builderChild;
                if ( isPublic( buildMethod ) && !isStatic( buildMethod ) && isBuildMethod( buildMethod )
                    && !hasArgs( buildMethod ) && returnsDeclaredType( buildMethod ) ) {

                    final TypeElement potentialImmutable = typeElement( buildMethod.getReturnType() );
                    if ( isImmutable( potentialImmutable ) ) {
                        potentials.add( BuilderMapping.builder()
                            .buildMethod( buildMethod )
                            .targetType( potentialImmutable ) );
                    }
                }
            }
        }
        return potentials;
    }

    private static boolean isImmutable(TypeElement immutableType) {
        boolean hasConstructor = false;
        boolean hasFinalFields = false;
        for ( Element child : immutableType.getEnclosedElements() ) {
            if ( child.getKind() == ElementKind.CONSTRUCTOR ) {
                ExecutableElement constructor = (ExecutableElement) child;
                hasConstructor = true;
                if ( child.getModifiers().contains( Modifier.PUBLIC ) && constructor.getParameters().isEmpty() ) {
                    return false;
                }
            }
            else if ( child.getKind() == ElementKind.FIELD ) {
                if ( child.getModifiers().contains( Modifier.FINAL ) ) {
                    hasFinalFields = true;
                }
            }
        }
        return hasConstructor && hasFinalFields;
    }

    private static boolean isPublic(ExecutableElement method) {
        return method.getModifiers().contains( Modifier.PUBLIC );
    }

    private static boolean isBuildMethod(ExecutableElement method) {
        return method.getSimpleName().contentEquals( BUILD_METHOD_NAME );
    }

    private static boolean returnsDeclaredType(ExecutableElement method) {
        return method.getReturnType().getKind() == TypeKind.DECLARED;
    }

    private static boolean isStatic(ExecutableElement method) {
        return method.getModifiers().contains( Modifier.STATIC );
    }

    private static boolean hasArgs(ExecutableElement method) {
        return method.getParameters().size() > 0;
    }

    private static TypeElement typeElement(TypeMirror mirror) {
        return (TypeElement) ( (DeclaredType) mirror ).asElement();
    }

    /**
     * Mostly just to avoid ugly wrapping lines.
     */
    private static ConcurrentHashMap<String, BuilderMapping> newBuilderMappingCache() {
        return new ConcurrentHashMap<String, BuilderMapping>();
    }
}
