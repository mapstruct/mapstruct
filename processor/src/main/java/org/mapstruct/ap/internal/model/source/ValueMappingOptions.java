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

import org.mapstruct.ap.internal.gem.ValueMappingGem;
import org.mapstruct.ap.internal.gem.ValueMappingsGem;
import org.mapstruct.ap.internal.util.FormattingMessager;
import org.mapstruct.ap.internal.util.Message;

import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ANY_REMAINING;
import static org.mapstruct.ap.internal.gem.MappingConstantsGem.ANY_UNMAPPED;

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

    public static void fromMappingsGem(ValueMappingsGem mappingsGem, ExecutableElement method,
                                       FormattingMessager messager, List<ValueMappingOptions> mappings) {

        boolean anyFound = false;
        for ( ValueMappingGem mappingGem : mappingsGem.value().get() ) {
            ValueMappingOptions mapping = fromMappingGem( mappingGem );
            if ( mapping != null ) {

                if ( !mappings.contains( mapping ) ) {
                    mappings.add( mapping );
                }
                else {
                    messager.printMessage(
                        method,
                        mappingGem.mirror(),
                        mappingGem.target().getAnnotationValue(),
                        Message.VALUEMAPPING_DUPLICATE_SOURCE,
                        mappingGem.source().get()
                    );
                }
                if ( ANY_REMAINING.equals( mapping.source )
                    || ANY_UNMAPPED.equals( mapping.source ) ) {
                    if ( anyFound ) {
                        messager.printMessage(
                            method,
                            mappingGem.mirror(),
                            mappingGem.target().getAnnotationValue(),
                            Message.VALUEMAPPING_ANY_AREADY_DEFINED,
                            mappingGem.source().get()
                        );
                    }
                    anyFound = true;
                }
            }
        }
    }

    public static ValueMappingOptions fromMappingGem(ValueMappingGem mapping ) {

        return new ValueMappingOptions( mapping.source().get(), mapping.target().get(), mapping.mirror(),
            mapping.source().getAnnotationValue(), mapping.target().getAnnotationValue() );
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
