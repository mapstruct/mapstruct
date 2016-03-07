/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
 import org.mapstruct.ap.internal.model.source.Method;
import org.mapstruct.ap.internal.model.source.SelectionParameters;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.model.source.ValueMapping;
import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import static org.mapstruct.ap.internal.util.Collections.first;
import org.mapstruct.ap.internal.util.Message;
import org.mapstruct.ap.internal.util.Strings;

/**
 * A {@link MappingMethod} which maps one enum type to another, optionally configured by one or more
 * {@link EnumMapping}s.
 *
 * @author Sjaak Derksen
 */
public class ValueMappingMethod extends MappingMethod {

    private final List<MappingEntry> valueMappings;
    private final String defaultTarget;
    private final String nullTarget;
    private final boolean throwIllegalArgumentException;

    public static class Builder {

        private SourceMethod method;
        private MappingBuilderContext ctx;
        private final List<ValueMapping> trueValueMappings = new ArrayList<ValueMapping>();
        private ValueMapping defaultTargetValue = null;
        private ValueMapping nullTargetValue = null;
        private boolean applyNamebasedMappings = true;


        public Builder mappingContext(MappingBuilderContext mappingContext) {
            this.ctx = mappingContext;
            return this;
        }

        public Builder souceMethod(SourceMethod sourceMethod) {
            this.method = sourceMethod;
            return this;
        }

        public Builder valueMappings(List<ValueMapping> valueMappings) {
            for ( ValueMapping valueMapping : valueMappings ) {
                if ( ValueMapping.ANY.equals( valueMapping.getSource() ) ) {
                    defaultTargetValue = valueMapping;
                }
                else if ( ValueMapping.ANY_UNMAPPED.equals( valueMapping.getSource() ) ) {
                    defaultTargetValue = valueMapping;
                    applyNamebasedMappings = false;
                }
                else if ( ValueMapping.NULL.equals( valueMapping.getSource() ) ) {
                    nullTargetValue = valueMapping;
                }
                else {
                    trueValueMappings.add( valueMapping );
                }
            }
            return this;
        }

        public ValueMappingMethod build( ) {

            // initialize all relevant parameters
            List<MappingEntry> mappingEntries = new ArrayList<MappingEntry>();
            String nullTarget = null;
            String defaultTarget = null;
            boolean throwIllegalArgumentException = false;

            // for now, we're only dealing with enum mappings, populate relevant parameters based on enum-2-enum
            if ( first( method.getSourceParameters() ).getType().isEnumType() && method.getResultType().isEnumType() ) {
                mappingEntries.addAll( enumToEnumMapping( method ) );

                if ( (nullTargetValue != null) && !ValueMapping.NULL.equals( nullTargetValue.getTarget() ) ) {
                    // absense nulltargetvalue reverts to null. Or it could be a deliberate choice to return null
                    nullTarget = nullTargetValue.getTarget();
                }
                if ( defaultTargetValue != null ) {
                    defaultTarget = defaultTargetValue.getTarget();
                }
                else {
                    throwIllegalArgumentException = true;
                }

            }

            // do before / after lifecycle mappings
            SelectionParameters selectionParameters = getSelectionParameters( method );
            List<LifecycleCallbackMethodReference> beforeMappingMethods
                = LifecycleCallbackFactory.beforeMappingMethods( method, selectionParameters, ctx );
            List<LifecycleCallbackMethodReference> afterMappingMethods
                = LifecycleCallbackFactory.afterMappingMethods( method, selectionParameters, ctx );


            // finallyn return a mapping
            return new ValueMappingMethod( method, mappingEntries, nullTarget, defaultTarget,
                throwIllegalArgumentException, beforeMappingMethods, afterMappingMethods );
        }

        private List<MappingEntry> enumToEnumMapping(SourceMethod method) {

            List<MappingEntry> mappings = new ArrayList<MappingEntry>();
            List<String> unmappedSourceConstants
                = new ArrayList<String>( first( method.getSourceParameters() ).getType().getEnumConstants() );


            if ( !reportErrorIfMappedEnumConstantsDontExist( method ) ) {
                return mappings;
            }

            // Start to fill the mappings with the defined valuemappings
            for ( ValueMapping valueMapping : trueValueMappings ) {
                String target = ValueMapping.NULL.equals( valueMapping.getTarget() ) ? null : valueMapping.getTarget();
                mappings.add( new MappingEntry( valueMapping.getSource(), target ) );
                unmappedSourceConstants.remove( valueMapping.getSource() );
            }


            // add mappings based on name
            if ( applyNamebasedMappings ) {

                // get all target constants
                List<String> targetConstants = method.getReturnType().getEnumConstants();
                for ( String sourceConstant : new ArrayList<String>( unmappedSourceConstants ) ) {
                    if ( targetConstants.contains( sourceConstant ) ) {
                        mappings.add( new MappingEntry( sourceConstant, sourceConstant ) );
                        unmappedSourceConstants.remove( sourceConstant );
                    }
                }

                if ( defaultTargetValue == null && !unmappedSourceConstants.isEmpty() ) {
                    // all sources should now be matched, there's no default to fall back to, so if sources remain,
                    // we have an issue.
                    ctx.getMessager().printMessage( method.getExecutable(),
                        Message.VALUE_MAPPING_UNMAPPED_SOURCES,
                        Strings.join( unmappedSourceConstants, ", " )
                    );

                }
            }
            return mappings;
        }

        private SelectionParameters getSelectionParameters(SourceMethod method) {
            BeanMappingPrism beanMappingPrism = BeanMappingPrism.getInstanceOn( method.getExecutable() );
            if ( beanMappingPrism != null ) {
                List<TypeMirror> qualifiers = beanMappingPrism.qualifiedBy();
                List<String> qualifyingNames = beanMappingPrism.qualifiedByName();
                TypeMirror resultType = beanMappingPrism.resultType();
                return new SelectionParameters( qualifiers, qualifyingNames, resultType );
            }
            return null;
        }

        private boolean reportErrorIfMappedEnumConstantsDontExist(SourceMethod method) {
            List<String> sourceEnumConstants = first( method.getSourceParameters() ).getType().getEnumConstants();
            List<String> targetEnumConstants = method.getReturnType().getEnumConstants();

            boolean foundIncorrectMapping = false;

            for ( ValueMapping mappedConstant : trueValueMappings ) {

                if ( !sourceEnumConstants.contains( mappedConstant.getSource() ) ) {
                    ctx.getMessager().printMessage( method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getSourceAnnotationValue(),
                        Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                        mappedConstant.getSource(),
                        first( method.getSourceParameters() ).getType()
                    );
                    foundIncorrectMapping = true;
                }
                if ( !ValueMapping.NULL.equals( mappedConstant.getTarget() )
                    && !targetEnumConstants.contains( mappedConstant.getTarget() ) ) {
                    ctx.getMessager().printMessage( method.getExecutable(),
                        mappedConstant.getMirror(),
                        mappedConstant.getTargetAnnotationValue(),
                        Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                        mappedConstant.getTarget(),
                        method.getReturnType()
                    );
                    foundIncorrectMapping = true;
                }
            }

            if ( defaultTargetValue != null && !ValueMapping.NULL.equals( defaultTargetValue.getTarget() )
                && !targetEnumConstants.contains( defaultTargetValue.getTarget() ) ) {
                ctx.getMessager().printMessage( method.getExecutable(),
                    defaultTargetValue.getMirror(),
                    defaultTargetValue.getTargetAnnotationValue(),
                    Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                    defaultTargetValue.getTarget(),
                    method.getReturnType()
                );
                foundIncorrectMapping = true;
            }

            if ( nullTargetValue != null && ValueMapping.NULL.equals( nullTargetValue.getTarget() )
                && !targetEnumConstants.contains( nullTargetValue.getTarget() ) ) {
                ctx.getMessager().printMessage( method.getExecutable(),
                    nullTargetValue.getMirror(),
                    nullTargetValue.getTargetAnnotationValue(),
                    Message.VALUEMAPPING_NON_EXISTING_CONSTANT,
                    nullTargetValue.getTarget(),
                    method.getReturnType()
                );
                foundIncorrectMapping = true;
            }

            return !foundIncorrectMapping;
        }
    }

    private ValueMappingMethod(Method method, List<MappingEntry> enumMappings, String nullTarget, String defaultTarget,
        boolean throwIllegalArgumentException, List<LifecycleCallbackMethodReference> beforeMappingMethods,
        List<LifecycleCallbackMethodReference> afterMappingMethods) {
        super( method, beforeMappingMethods, afterMappingMethods );
        this.valueMappings = enumMappings;
        this.nullTarget = nullTarget;
        this.defaultTarget = defaultTarget;
        this.throwIllegalArgumentException = throwIllegalArgumentException;
    }

    public List<MappingEntry> getValueMappings() {
        return valueMappings;
    }

    public String getDefaultTarget() {
        return defaultTarget;
    }

    public String getNullTarget() {
        return nullTarget;
    }

    public boolean isThrowIllegalArgumentException() {
        return throwIllegalArgumentException;
    }

    public Parameter getSourceParameter() {
        return first( getParameters() );
    }

    public static class MappingEntry {
        private final String source;
        private final String target;

        MappingEntry( String source, String target ) {
            this.source = source;
            this.target = target;
        }

        public String getSource() {
            return source;
        }

        public String getTarget() {
            return target;
        }




    }
}
