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
package org.mapstruct.ap.internal.model.source;

import java.util.List;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;
import org.mapstruct.ap.internal.prism.ValueMappingPrism;
import org.mapstruct.ap.internal.prism.ValueMappingTypePrism;
import org.mapstruct.ap.internal.prism.ValueMappingsPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

/**
 * Represents the mapping between one value constant and another.
 *
 * @author Sjaak Derksen
 */
public class ValueMapping {

    private final String source;
    private final ValueMappingTypePrism valueMappingType;
    private final String target;
    private final AnnotationMirror mirror;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;

    public static void fromMappingsPrism(ValueMappingsPrism mappingsAnnotation, ExecutableElement method,
        FormattingMessager messager, List<ValueMapping> mappings) {

        for ( ValueMappingPrism mappingPrism : mappingsAnnotation.value() ) {
            ValueMapping mapping = fromMappingPrism( mappingPrism, method, messager );
            if ( mapping != null ) {

                if ( !mappings.contains( mapping ) ) {
                    mappings.add( mapping );
                }
                else {
                    // do error handling
                    if ( ValueMappingTypePrism.SOURCE.equals( mapping.valueMappingType ) ) {
                        messager.printMessage(
                            method,
                            mappingPrism.mirror,
                            mappingPrism.values.target(),
                            Message.VALUEMAPPING_DUPLICATE_SOURCE,
                            mappingPrism.source()
                        );
                    }
                    else if ( ValueMappingTypePrism.DEFAULT.equals( mapping.valueMappingType ) ) {
                        messager.printMessage(
                            method,
                            mappingPrism.mirror,
                            mappingPrism.values.target(),
                            Message.VALUEMAPPING_DUPLICATE_DEFAULT
                        );
                    }
                    else {
                        messager.printMessage(
                            method,
                            mappingPrism.mirror,
                            mappingPrism.values.target(),
                            Message.VALUEMAPPING_DUPLICATE_NULL
                        );
                    }
                }
            }
        }
    }

    public static ValueMapping fromMappingPrism(ValueMappingPrism mappingPrism, ExecutableElement element,
                                           FormattingMessager messager) {


        if ( !mappingPrism.target().isEmpty() && mappingPrism.targetIsNull() ) {
            messager.printMessage( element,
                mappingPrism.mirror,
                mappingPrism.values.target(),
                Message.VALUEMAPPING_NON_EMPTY_TARGET
            );
            return null;
        }

        ValueMappingTypePrism valueMappingType
            = mappingPrism.values.valueMappingType() == null
                ? ValueMappingTypePrism.SOURCE
                : ValueMappingTypePrism.valueOf( mappingPrism.valueMappingType() );

        if ( !mappingPrism.source().isEmpty()
            && valueMappingType == ValueMappingTypePrism.DEFAULT ) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                mappingPrism.values.target(),
                Message.VALUEMAPPING_NON_EMPTY_SOURCE_FOR_DEFAULT
            );
        }

       if ( !mappingPrism.source().isEmpty()
           && valueMappingType == ValueMappingTypePrism.DEFAULT_AFTER_APPLYING_NAME_BASED_MAPPINGS )  {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                mappingPrism.values.target(),
                Message.VALUEMAPPING_NON_EMPTY_SOURCE_FOR_DEFAULT_AFTER_APPLYING_NAME_BASED_MAPPINGS
            );
        }

        if ( !mappingPrism.source().isEmpty() && valueMappingType == ValueMappingTypePrism.NULL) {
            messager.printMessage(
                element,
                mappingPrism.mirror,
                mappingPrism.values.target(),
                Message.VALUEMAPPING_NON_EMPTY_SOURCE_FOR_NULL
            );
        }

        String target;
        if ( !mappingPrism.targetIsNull() ) {
            target = mappingPrism.target();
        }
        else {
            target = null;
        }

        return new ValueMapping( mappingPrism.source(), valueMappingType, target, mappingPrism.mirror,
            mappingPrism.values.source(), mappingPrism.values.target() );
    }


    private ValueMapping(String source, ValueMappingTypePrism valueMappingType, String target, AnnotationMirror mirror,
                    AnnotationValue sourceAnnotationValue, AnnotationValue targetAnnotationValue ) {
        this.source = source;
        this.valueMappingType = valueMappingType;
        this.target = target;
        this.mirror = mirror;
        this.sourceAnnotationValue = sourceAnnotationValue;
        this.targetAnnotationValue = targetAnnotationValue;
    }

    /**
     * @return the name of the constant in the source.
     */
    public String getSource() {
        return source;
    }

    /**
     * @return the value mapping type.
     */
    public ValueMappingTypePrism getValueMappingType() {
        return valueMappingType;
    }

    /**
     * @return the name of the constant in the target.
     */
    public String getTarget() {
        return target;
    }

    public AnnotationMirror getMirror() {
        return mirror;
    }

    public AnnotationValue getSourceAnnotationValue() {
        return sourceAnnotationValue;
    }

    public AnnotationValue getTargetAnnotationValue() {
        return targetAnnotationValue;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        final ValueMapping other = (ValueMapping) obj;
        if ( (this.source == null) ? (other.source != null) : !this.source.equals( other.source ) ) {
            return false;
        }
        switch ( this.valueMappingType ) {
            case DEFAULT:
            case DEFAULT_AFTER_APPLYING_NAME_BASED_MAPPINGS:
                return ValueMappingTypePrism.DEFAULT.equals( other.valueMappingType )
                   || ValueMappingTypePrism.DEFAULT_AFTER_APPLYING_NAME_BASED_MAPPINGS.equals( other.valueMappingType );
            default:
                return this.valueMappingType.equals( other.valueMappingType );
        }
    }


}
