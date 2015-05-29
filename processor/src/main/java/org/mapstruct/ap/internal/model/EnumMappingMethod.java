/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.model.common.Parameter;
import org.mapstruct.ap.internal.model.source.EnumMapping;
import org.mapstruct.ap.internal.model.source.Mapping;
import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

import static org.mapstruct.ap.internal.util.Collections.first;

/**
 * A {@link MappingMethod} which maps one enum type to another, optionally configured by one or more
 * {@link EnumMapping}s.
 *
 * @author Gunnar Morling
 */
public class EnumMappingMethod extends MappingMethod {

    private final List<EnumMapping> enumMappings;

    public static class Builder {

        private SourceMethod method;
        private MappingBuilderContext ctx;

        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder souceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public EnumMappingMethod build() {

            if ( !reportErrorIfMappedEnumConstantsDontExist( method )
                || !reportErrorIfSourceEnumConstantsWithoutCorrespondingTargetConstantAreNotMapped( method ) ) {
                return null;
            }

            List<EnumMapping> enumMappings = new ArrayList<EnumMapping>();

            List<String> sourceEnumConstants = first( method.getSourceParameters() ).getType().getEnumConstants();

            for ( String enumConstant : sourceEnumConstants ) {
                List<Mapping> mappedConstants = method.getMappingBySourcePropertyName( enumConstant );

                if ( mappedConstants.isEmpty() ) {
                    enumMappings.add( new EnumMapping( enumConstant, enumConstant ) );
                }
                else if ( mappedConstants.size() == 1 ) {
                    enumMappings.add(
                        new EnumMapping(
                            enumConstant, first( mappedConstants ).getTargetName()
                        )
                    );
                }
                else {
                    List<String> targetConstants = new ArrayList<String>( mappedConstants.size() );
                    for ( Mapping mapping : mappedConstants ) {
                        targetConstants.add( mapping.getTargetName() );
                    }
                    ctx.getMessager().printMessage( method.getExecutable(),
                        Message.ENUMMAPPING_MULTIPLE_TARGETS,
                        enumConstant,
                        Strings.join( targetConstants, ", " )
                    );
                }
            }

            List<TypeMirror> qualifiers = getQualifiers( method );

            List<LifecycleCallbackMethodReference> beforeMappingMethods =
                LifecycleCallbackFactory.beforeMappingMethods( method, qualifiers, ctx );
            List<LifecycleCallbackMethodReference> afterMappingMethods =
                LifecycleCallbackFactory.afterMappingMethods( method, qualifiers, ctx );

            return new EnumMappingMethod( method, enumMappings, beforeMappingMethods, afterMappingMethods );
        }

        private static List<TypeMirror> getQualifiers(SourceMethod method) {
            BeanMappingPrism beanMappingPrism = BeanMappingPrism.getInstanceOn( method.getExecutable() );

            if ( beanMappingPrism != null ) {
                return beanMappingPrism.qualifiedBy();
            }

            return null;
        }

        private boolean reportErrorIfMappedEnumConstantsDontExist(SourceMethod method) {
            List<String> sourceEnumConstants = first( method.getSourceParameters() ).getType().getEnumConstants();
            List<String> targetEnumConstants = method.getReturnType().getEnumConstants();

            boolean foundIncorrectMapping = false;

            for ( List<Mapping> mappedConstants : method.getMappingOptions().getMappings().values() ) {
                for ( Mapping mappedConstant : mappedConstants ) {

                    if ( mappedConstant.getSourceName() == null ) {
                        ctx.getMessager().printMessage( method.getExecutable(),
                            mappedConstant.getMirror(),
                            Message.ENUMMAPPING_UNDEFINED_SOURCE
                        );
                        foundIncorrectMapping = true;
                    }
                    else if ( !sourceEnumConstants.contains( mappedConstant.getSourceName() ) ) {
                        ctx.getMessager().printMessage( method.getExecutable(),
                            mappedConstant.getMirror(),
                            mappedConstant.getSourceAnnotationValue(),
                            Message.ENUMMAPPING_NON_EXISTING_CONSTANT,
                            mappedConstant.getSourceName(),
                            first( method.getSourceParameters() ).getType()
                        );
                        foundIncorrectMapping = true;
                    }
                    if ( mappedConstant.getTargetName() == null ) {
                        ctx.getMessager().printMessage( method.getExecutable(),
                            mappedConstant.getMirror(),
                            Message.ENUMMAPPING_UNDEFINED_TARGET
                        );
                        foundIncorrectMapping = true;
                    }
                    else if ( !targetEnumConstants.contains( mappedConstant.getTargetName() ) ) {
                        ctx.getMessager().printMessage( method.getExecutable(),
                            mappedConstant.getMirror(),
                            mappedConstant.getTargetAnnotationValue(),
                            Message.ENUMMAPPING_NON_EXISTING_CONSTANT,
                            mappedConstant.getTargetName(),
                            method.getReturnType()
                        );
                        foundIncorrectMapping = true;
                    }
                }
            }

            return !foundIncorrectMapping;
        }

        private boolean reportErrorIfSourceEnumConstantsWithoutCorrespondingTargetConstantAreNotMapped(
            SourceMethod method) {

            List<String> sourceEnumConstants = first( method.getSourceParameters() ).getType().getEnumConstants();
            List<String> targetEnumConstants = method.getReturnType().getEnumConstants();
            List<String> unmappedSourceEnumConstants = new ArrayList<String>();

            for ( String sourceEnumConstant : sourceEnumConstants ) {
                if ( !targetEnumConstants.contains( sourceEnumConstant )
                    && method.getMappingBySourcePropertyName( sourceEnumConstant ).isEmpty() ) {
                    unmappedSourceEnumConstants.add( sourceEnumConstant );
                }
            }

            if ( !unmappedSourceEnumConstants.isEmpty() ) {
                ctx.getMessager().printMessage( method.getExecutable(),
                    Message.ENUMMAPPING_UNMAPPED_TARGETS,
                    Strings.join( unmappedSourceEnumConstants, ", " )
                );
            }

            return unmappedSourceEnumConstants.isEmpty();
        }

    }

    private EnumMappingMethod(Method method, List<EnumMapping> enumMappings,
                              List<LifecycleCallbackMethodReference> beforeMappingMethods,
                              List<LifecycleCallbackMethodReference> afterMappingMethods) {
        super( method, beforeMappingMethods, afterMappingMethods );
        this.enumMappings = enumMappings;
    }

    public List<EnumMapping> getEnumMappings() {
        return enumMappings;
    }

    public Parameter getSourceParameter() {
        return first( getParameters() );
    }
}
