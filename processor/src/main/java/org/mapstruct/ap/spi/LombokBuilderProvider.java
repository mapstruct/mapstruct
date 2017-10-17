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

import static javax.lang.model.util.ElementFilter.methodsIn;
import static javax.lang.model.util.ElementFilter.typesIn;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.mapstruct.ap.shared.TypeHierarchyErroneousException;

/**
 * Implementation of {@link BuilderProvider} that works for lombok
 */
public class LombokBuilderProvider implements BuilderProvider {

    private static final String BUILD_METHOD_ATTRIBUTE = "buildMethodName";
    private static final String BUILDER_METHOD_ATTRIBUTE = "builderMethodName";
    private static final String BUILDER_CLASS_ATTRIBUTE = "builderClassName";

    private static final String BUILD_METHOD_DEFAULT = "build";
    private static final String BUILDER_METHOD_DEFAULT = "builder";

    private static final AnnotationMirrorHelper NULL_CONFIG = nullLombokConfig();

    /**
     * Internal cache of known builders
     */
    private final ConcurrentHashMap<String, BuilderInfo> foundBuilders;

    /**
     * Internal cache of lombok builder types
     */
    private final ConcurrentHashMap<String, AnnotationMirrorHelper> lombokConfigs;

    public LombokBuilderProvider() {
        this.foundBuilders = new ConcurrentHashMap<String, BuilderInfo>();
        this.lombokConfigs = new ConcurrentHashMap<String, AnnotationMirrorHelper>();
    }

    @Override
    public BuilderInfo findBuildTarget(TypeMirror toInspect, Elements elements, Types types) {
        if ( toInspect.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        final TypeElement typeElement = typeElement( toInspect, types );
        final Element enclosingElement = typeElement.getEnclosingElement();
        if ( enclosingElement != null && enclosingElement.getKind() == ElementKind.CLASS ) {
            return findBuilder( enclosingElement.asType(), elements, types );
        }
        else {
            return null;
        }
    }

    @Override
    public BuilderInfo findBuilder(TypeMirror toInspect, Elements elements, Types types) {
        if ( toInspect.getKind() != TypeKind.DECLARED ) {
            return null;
        }

        final TypeElement immutableType = typeElement( toInspect, types );
        final String immutableFQN = immutableType.getQualifiedName().toString();
        if ( foundBuilders.containsKey( immutableFQN ) ) {
            return foundBuilders.get( immutableFQN );
        }

        if ( !lombokConfigs.containsKey( immutableFQN ) ) {
            AnnotationMirrorHelper lombokConfig = NULL_CONFIG;
            for ( AnnotationMirror annotation : immutableType.getAnnotationMirrors() ) {
                final TypeElement annotationType = typeElement( annotation.getAnnotationType(), types );
                if ( annotationType.getQualifiedName().contentEquals( "lombok.Builder" ) ) {
                    lombokConfig = new AnnotationMirrorHelper( elements.getElementValuesWithDefaults( annotation ) );
                }
            }
            lombokConfigs.put( immutableFQN, lombokConfig );
        }

        final AnnotationMirrorHelper lombokConfig = lombokConfigs.get( immutableFQN );
        if ( lombokConfig != NULL_CONFIG ) {
            return resolveLombokBuilder( immutableType, lombokConfig );
        }

        return null;
    }

    private BuilderInfo resolveLombokBuilder(TypeElement type, AnnotationMirrorHelper lombokConfig) {
        TypeElement builderType = findBuilderType( type, lombokConfig );
        ExecutableElement buildMethod = findBuildMethod( builderType, lombokConfig );
        ExecutableElement builderMethod = findBuilderMethod( type, lombokConfig );
        if ( builderType != null && buildMethod != null && builderMethod != null ) {
            final BuilderInfo builderInfo = BuilderInfo.builder()
                .builderType( builderType.asType() )
                .targetType( type.asType() )
                .builderCreationMethod( builderMethod )
                .buildMethod( buildMethod )
                .build();
            foundBuilders.put( type.getQualifiedName().toString(), builderInfo );
            return builderInfo;
        }
        else {
            throw new TypeHierarchyErroneousException( type );
        }
    }

    private TypeElement findBuilderType(TypeElement type, AnnotationMirrorHelper lombokConfig) {
        String defaultClassName = type.getSimpleName() + "Builder";
        final String simpleName = lombokConfig.getAttribute( BUILDER_CLASS_ATTRIBUTE, defaultClassName );
        final String builderClassName = type.getQualifiedName() + "." + simpleName;

        for ( TypeElement typeElement : typesIn( type.getEnclosedElements() ) ) {
            if ( typeElement.getQualifiedName().contentEquals( builderClassName ) ) {
                return typeElement;
            }
        }

        return null;
    }

    private ExecutableElement findBuildMethod(TypeElement builderType, AnnotationMirrorHelper lombokConfig) {
        if ( builderType == null ) {
            return null;
        }

        final String buildMethodName = lombokConfig.getAttribute( BUILD_METHOD_ATTRIBUTE, BUILD_METHOD_DEFAULT );

        for ( ExecutableElement buildMethod : methodsIn( builderType.getEnclosedElements() ) ) {
            if ( !buildMethod.getModifiers().contains( Modifier.STATIC )
                && buildMethod.getSimpleName().contentEquals( buildMethodName ) ) {

                return buildMethod;
            }
        }
        return null;
    }

    private ExecutableElement findBuilderMethod(TypeElement targetType, AnnotationMirrorHelper lombokConfig) {
        if ( targetType == null ) {
            return null;
        }

        final String builderMethodName = lombokConfig.getAttribute( BUILDER_METHOD_ATTRIBUTE, BUILDER_METHOD_DEFAULT );

        for ( ExecutableElement builderMethod : methodsIn( targetType.getEnclosedElements() ) ) {
            if ( builderMethod.getModifiers().contains( Modifier.STATIC )
                && builderMethod.getSimpleName().contentEquals( builderMethodName ) ) {

                return builderMethod;
            }
        }
        return null;
    }

    private static TypeElement typeElement(TypeMirror mirror, Types types) {
        return (TypeElement) types.asElement( mirror );
    }

    public static class AnnotationMirrorHelper {
        private final Map<String, ? extends AnnotationValue> valuesWithDefaults;

        AnnotationMirrorHelper(Map<? extends ExecutableElement, ? extends AnnotationValue> annotationValues) {
            Map<String, AnnotationValue> values = new HashMap<String, AnnotationValue>();

            for ( ExecutableElement key : annotationValues.keySet() ) {
                values.put( key.getSimpleName().toString(), annotationValues.get( key ) );
            }
            this.valuesWithDefaults = Collections.unmodifiableMap( values );
        }

        public String getAttribute(String key, String defaultValue) {
            if ( !valuesWithDefaults.containsKey( key ) ) {
                return defaultValue;
            }
            return getAttribute( key, String.class );
        }

        @SuppressWarnings( {"unchecked"})
        public <X> X getAttribute(String key, Class<X> type) {
            final AnnotationValue annotationValue = valuesWithDefaults.get( key );
            if ( annotationValue == null ) {
                return null;
            } else {
                return (X) annotationValue.getValue();
            }
        }
    }

    private static AnnotationMirrorHelper nullLombokConfig() {
        final Map<? extends ExecutableElement, ? extends AnnotationValue> empty =
            Collections.emptyMap();
        return new AnnotationMirrorHelper( empty );
    }
}
