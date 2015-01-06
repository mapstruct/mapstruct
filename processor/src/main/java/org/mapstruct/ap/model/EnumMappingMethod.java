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
package org.mapstruct.ap.model;

import java.util.ArrayList;
import java.util.List;
import javax.tools.Diagnostic;

import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.source.EnumMapping;
import org.mapstruct.ap.model.source.Mapping;
import org.mapstruct.ap.model.source.Method;
import org.mapstruct.ap.model.source.SourceMethod;
import org.mapstruct.ap.util.Strings;

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

            List<String> sourceEnumConstants
                = method.getSourceParameters().iterator().next().getType().getEnumConstants();

            for ( String enumConstant : sourceEnumConstants ) {
                List<Mapping> mappedConstants = method.getMappingBySourcePropertyName( enumConstant );

                if ( mappedConstants.isEmpty() ) {
                    enumMappings.add( new EnumMapping( enumConstant, enumConstant ) );
                }
                else if ( mappedConstants.size() == 1 ) {
                    enumMappings.add(
                        new EnumMapping(
                            enumConstant, mappedConstants.iterator().next().getTargetName()
                        )
                    );
                }
                else {
                    List<String> targetConstants = new ArrayList<String>( mappedConstants.size() );
                    for ( Mapping mapping : mappedConstants ) {
                        targetConstants.add( mapping.getTargetName() );
                    }
                    ctx.getMessager().printMessage(
                        Diagnostic.Kind.ERROR,
                        String.format(
                            "One enum constant must not be mapped to more than one target constant, "
                                + "but constant %s is mapped to %s.",
                            enumConstant,
                            Strings.join( targetConstants, ", " )
                        ),
                        method.getExecutable()
                    );
                }
            }

            return new EnumMappingMethod( method, enumMappings );
        }

        private boolean reportErrorIfMappedEnumConstantsDontExist(SourceMethod method) {
            List<String> sourceEnumConstants =
                method.getSourceParameters().iterator().next().getType().getEnumConstants();
            List<String> targetEnumConstants = method.getReturnType().getEnumConstants();

            boolean foundIncorrectMapping = false;

            for ( List<Mapping> mappedConstants : method.getMappings().values() ) {
                for ( Mapping mappedConstant : mappedConstants ) {

                    if ( mappedConstant.getSourceName() == null ) {
                        ctx.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "A source constant must be specified for mappings of an enum mapping method.",
                            method.getExecutable(),
                            mappedConstant.getMirror()
                        );
                        foundIncorrectMapping = true;
                    }
                    else if ( !sourceEnumConstants.contains( mappedConstant.getSourceName() ) ) {
                        ctx.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            String.format(
                                "Constant %s doesn't exist in enum type %s.",
                                mappedConstant.getSourceName(),
                                method.getSourceParameters().iterator().next().getType()
                            ),
                            method.getExecutable(),
                            mappedConstant.getMirror(),
                            mappedConstant.getSourceAnnotationValue()
                        );
                        foundIncorrectMapping = true;
                    }
                    if ( mappedConstant.getTargetName() == null ) {
                        ctx.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            "A target constant must be specified for mappings of an enum mapping method.",
                            method.getExecutable(),
                            mappedConstant.getMirror()
                        );
                        foundIncorrectMapping = true;
                    }
                    else if ( !targetEnumConstants.contains( mappedConstant.getTargetName() ) ) {
                        ctx.getMessager().printMessage(
                            Diagnostic.Kind.ERROR,
                            String.format(
                                "Constant %s doesn't exist in enum type %s.",
                                mappedConstant.getTargetName(),
                                method.getReturnType()
                            ),
                            method.getExecutable(),
                            mappedConstant.getMirror(),
                            mappedConstant.getTargetAnnotationValue()
                        );
                        foundIncorrectMapping = true;
                    }
                }
            }

            return !foundIncorrectMapping;
        }

        private boolean reportErrorIfSourceEnumConstantsWithoutCorrespondingTargetConstantAreNotMapped(
            SourceMethod method) {

            List<String> sourceEnumConstants =
                method.getSourceParameters().iterator().next().getType().getEnumConstants();
            List<String> targetEnumConstants = method.getReturnType().getEnumConstants();
            List<String> unmappedSourceEnumConstants = new ArrayList<String>();

            for ( String sourceEnumConstant : sourceEnumConstants ) {
                if ( !targetEnumConstants.contains( sourceEnumConstant )
                    && method.getMappingBySourcePropertyName( sourceEnumConstant ).isEmpty() ) {
                    unmappedSourceEnumConstants.add( sourceEnumConstant );
                }
            }

            if ( !unmappedSourceEnumConstants.isEmpty() ) {
                ctx.getMessager().printMessage(
                    Diagnostic.Kind.ERROR,
                    String.format(
                        "The following constants from the source enum have no corresponding constant in the "
                            + "target enum and must be be mapped via @Mapping: %s",
                        Strings.join( unmappedSourceEnumConstants, ", " )
                    ),
                    method.getExecutable()
                );
            }

            return unmappedSourceEnumConstants.isEmpty();
        }

    }

    private EnumMappingMethod(Method method, List<EnumMapping> enumMappings) {
        super( method );
        this.enumMappings = enumMappings;
    }

    public List<EnumMapping> getEnumMappings() {
        return enumMappings;
    }

    public Parameter getSourceParameter() {
        return getParameters().iterator().next();
    }
}
