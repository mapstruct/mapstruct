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
package org.mapstruct.ap.internal.model.source;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.prism.ReportingPolicyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents an bean mapping as configured via {@code @BeanMapping}.
 *
 * @author Sjaak Derksen
 */
public class BeanMapping {

    private final SelectionParameters selectionParameters;
    private final NullValueMappingStrategyPrism nullValueMappingStrategy;
    private final ReportingPolicyPrism reportingPolicy;

    public static BeanMapping fromPrism(BeanMappingPrism beanMapping, ExecutableElement method,
        FormattingMessager messager, Types typeUtils) {

        if ( beanMapping == null ) {
            return null;
        }

        boolean resultTypeIsDefined = !TypeKind.VOID.equals( beanMapping.resultType().getKind() );
        boolean builderTypeIsDefined = !TypeKind.VOID.equals( beanMapping.builderType().getKind() );
        TypeMirror builderType = builderTypeIsDefined ? beanMapping.builderType() : null;

        NullValueMappingStrategyPrism nullValueMappingStrategy =
            null == beanMapping.values.nullValueMappingStrategy()
                            ? null
                            : NullValueMappingStrategyPrism.valueOf( beanMapping.nullValueMappingStrategy() );

        if ( !builderTypeIsDefined && !resultTypeIsDefined && beanMapping.qualifiedBy().isEmpty() &&
            beanMapping.qualifiedByName().isEmpty()
            && ( nullValueMappingStrategy == null ) ) {

            messager.printMessage( method, Message.BEANMAPPING_NO_ELEMENTS );
        }

        SelectionParameters cmp = new SelectionParameters(
            beanMapping.qualifiedBy(),
            beanMapping.qualifiedByName(),
            resultTypeIsDefined ? beanMapping.resultType() : null,
            builderType,
            typeUtils
        );



        //TODO Do we want to add the reporting policy to the BeanMapping as well? To give more granular support?
        return new BeanMapping( cmp, nullValueMappingStrategy, null );
    }

    /**
     * This method should be used to generate BeanMappings for our internal generated Mappings. Like forged update
     * methods.
     *
     * @return bean mapping that needs to be used for Mappings
     */
    public static BeanMapping forForgedMethods() {
        return new BeanMapping( null, null, ReportingPolicyPrism.IGNORE );
    }

    private BeanMapping(SelectionParameters selectionParameters, NullValueMappingStrategyPrism nvms,
                        ReportingPolicyPrism reportingPolicy) {
        this.selectionParameters = selectionParameters;
        this.nullValueMappingStrategy = nvms;
        this.reportingPolicy = reportingPolicy;
    }

    public SelectionParameters getSelectionParameters() {
        return selectionParameters;
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return nullValueMappingStrategy;
    }

    public ReportingPolicyPrism getReportingPolicy() {
        return reportingPolicy;
    }

    public TypeMirror getBuilderTypeMirror() {
        return selectionParameters != null ? selectionParameters.getBuilderType() : null;
    }
}
