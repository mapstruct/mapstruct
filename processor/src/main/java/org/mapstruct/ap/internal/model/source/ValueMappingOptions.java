/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.List;
import java.util.Objects;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.ExecutableElement;

import org.mapstruct.ap.internal.prism.ValueMappingPrism;
import org.mapstruct.ap.internal.prism.ValueMappingsPrism;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

import static org.mapstruct.ap.internal.prism.MappingConstantsPrism.ANY_REMAINING;
import static org.mapstruct.ap.internal.prism.MappingConstantsPrism.ANY_UNMAPPED;

/**
 * Represents the mapping between one value constant and another.
 *
 * @author Sjaak Derksen
 */
public class ValueMappingOptions {

    private final String source;
    private final String target;
    private final AnnotationMirror mirror;
    private final AnnotationValue sourceAnnotationValue;
    private final AnnotationValue targetAnnotationValue;

    public static void fromMappingsPrism(ValueMappingsPrism mappingsAnnotation, ExecutableElement method,
        FormattingMessager messager, List<ValueMappingOptions> mappings) {

        boolean anyFound = false;
        for ( ValueMappingPrism mappingPrism : mappingsAnnotation.value() ) {
            ValueMappingOptions mapping = fromMappingPrism( mappingPrism );
            if ( mapping != null ) {

                if ( !mappings.contains( mapping ) ) {
                    mappings.add( mapping );
                }
                else {
                    messager.printMessage(
                        method,
                        mappingPrism.mirror,
                        mappingPrism.values.target(),
                        Message.VALUEMAPPING_DUPLICATE_SOURCE,
                        mappingPrism.source()
                    );
                }
                if ( ANY_REMAINING.equals( mapping.source )
                    || ANY_UNMAPPED.equals( mapping.source ) ) {
                    if ( anyFound ) {
                        messager.printMessage(
                            method,
                            mappingPrism.mirror,
                            mappingPrism.values.target(),
                            Message.VALUEMAPPING_ANY_AREADY_DEFINED,
                            mappingPrism.source()
                        );
                    }
                    anyFound = true;
                }
            }
        }
    }

    public static ValueMappingOptions fromMappingPrism(ValueMappingPrism mappingPrism ) {

        return new ValueMappingOptions( mappingPrism.source(), mappingPrism.target(), mappingPrism.mirror,
            mappingPrism.values.source(), mappingPrism.values.target() );
    }

    private ValueMappingOptions(String source, String target, AnnotationMirror mirror,
                                AnnotationValue sourceAnnotationValue, AnnotationValue targetAnnotationValue ) {
        this.source = source;
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

    public ValueMappingOptions inverse() {
        ValueMappingOptions result;
        if ( !(ANY_REMAINING.equals( source ) || ANY_UNMAPPED.equals( source ) ) ) {
            result = new ValueMappingOptions(
                target,
                source,
                mirror,
                sourceAnnotationValue,
                targetAnnotationValue );
        }
        else {
            result = null;
        }
        return result;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.source != null ? this.source.hashCode() : 0);
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
        final ValueMappingOptions other = (ValueMappingOptions) obj;
        return Objects.equals( this.source, other.source );
    }
}
