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
package org.mapstruct.ap.model.source;

import java.util.List;
import org.mapstruct.ap.util.FormattingMessager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.mapstruct.ap.prism.BeanMappingPrism;
import org.mapstruct.ap.util.Message;


/**
 * Represents an bean mapping as configured via {@code @BeanMapping}.
 *
 * @author Sjaak Derksen
 */
public class BeanMapping {

    private final List<TypeMirror> qualifiers;
    private final TypeMirror resultType;

    public static BeanMapping fromPrism( BeanMappingPrism beanMapping, ExecutableElement method,
                                         FormattingMessager messager ) {
        if ( beanMapping == null ) {
            return null;
        }

        boolean resultTypeIsDefined = !TypeKind.VOID.equals( beanMapping.resultType().getKind() );
        if ( !resultTypeIsDefined && beanMapping.qualifiedBy().isEmpty() ) {
            messager.printMessage( Diagnostic.Kind.ERROR, method, Message.beanmapping_noelements );
        }

        return new BeanMapping(
            beanMapping.qualifiedBy(),
            resultTypeIsDefined ? beanMapping.resultType() : null
        );
    }

    private BeanMapping( List<TypeMirror> qualifiers, TypeMirror mirror) {
        this.qualifiers = qualifiers;
        this.resultType = mirror;
    }

    public List<TypeMirror> getQualifiers() {
        return qualifiers;
    }

    public TypeMirror getResultType() {
        return resultType;
    }

}
