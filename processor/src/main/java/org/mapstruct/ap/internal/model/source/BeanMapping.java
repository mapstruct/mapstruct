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
package org.mapstruct.ap.internal.model.source;

import java.util.List;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

import org.mapstruct.ap.internal.prism.BeanMappingPrism;
import org.mapstruct.ap.internal.prism.NullValueMappingStrategyPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;


/**
 * Represents an bean mapping as configured via {@code @BeanMapping}.
 *
 * @author Sjaak Derksen
 */
public class BeanMapping {

    private final List<TypeMirror> qualifiers;
    private final TypeMirror resultType;
    private final NullValueMappingStrategyPrism nullValueMappingStrategy;

    public static BeanMapping fromPrism(BeanMappingPrism beanMapping, ExecutableElement method,
        FormattingMessager messager) {

        if ( beanMapping == null ) {
            return null;
        }

        boolean resultTypeIsDefined = !TypeKind.VOID.equals( beanMapping.resultType().getKind() );

        NullValueMappingStrategyPrism nullValueMappingStrategy =
            null == beanMapping.values.nullValueMappingStrategy()
                            ? null
                            : NullValueMappingStrategyPrism.valueOf( beanMapping.nullValueMappingStrategy() );

        if ( !resultTypeIsDefined && beanMapping.qualifiedBy().isEmpty()
            && ( nullValueMappingStrategy == null ) ) {

            messager.printMessage( method, Message.BEANMAPPING_NO_ELEMENTS );
        }

        return new BeanMapping(
            beanMapping.qualifiedBy(),
            resultTypeIsDefined ? beanMapping.resultType() : null,
            nullValueMappingStrategy
        );
    }

    private BeanMapping(List<TypeMirror> qualifiers, TypeMirror mirror, NullValueMappingStrategyPrism nvms) {

        this.qualifiers = qualifiers;
        this.resultType = mirror;
        this.nullValueMappingStrategy = nvms;
    }

    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    public TypeMirror getResultType() {
        return resultType;
    }

    public NullValueMappingStrategyPrism getNullValueMappingStrategy() {
        return nullValueMappingStrategy;
    }

}
