/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.beanmapping;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.model.source.MappingOptions;
import org.mapstruct.ap.internal.model.source.SourceMethod;
import org.mapstruct.ap.internal.util.FormattingMessager;

public class MappingReferences {

    private static final MappingReferences EMPTY = new MappingReferences( Collections.emptySet(),  false );

    private final Set<MappingReference> mappingReferences;
    private final boolean restrictToDefinedMappings;
    private final boolean forForgedMethods;

    public static MappingReferences empty() {
        return EMPTY;
    }

    public static MappingReferences forSourceMethod(SourceMethod sourceMethod,
        Type targetType,
        Set<String> targetProperties,
        FormattingMessager messager,
        TypeFactory typeFactory) {

        Set<MappingReference> references = new LinkedHashSet<>();

        for ( MappingOptions mapping : sourceMethod.getOptions().getMappings() ) {

            // handle source reference
            SourceReference sourceReference = new SourceReference.BuilderFromMapping().mapping( mapping )
                                                                                      .method( sourceMethod )
                                                                                      .messager( messager )
                                                                                      .typeFactory( typeFactory )
                                                                                      .build();

            // handle target reference
            TargetReference targetReference = new TargetReference.Builder().mapping( mapping )
                                                                           .method( sourceMethod )
                                                                           .messager( messager )
                                                                           .typeFactory( typeFactory )
                                                                           .targetProperties( targetProperties )
                                                                           .targetType( targetType )
                                                                           .build();

            // add when inverse is also valid
            MappingReference mappingReference = new MappingReference( mapping, targetReference, sourceReference );
            if ( isValidWhenInversed( mappingReference ) ) {
                references.add( mappingReference );
            }
        }
        return new MappingReferences( references, false );
    }

    public MappingReferences(Set<MappingReference> mappingReferences, boolean restrictToDefinedMappings) {
        this.mappingReferences = mappingReferences;
        this.restrictToDefinedMappings = restrictToDefinedMappings;
        this.forForgedMethods = restrictToDefinedMappings;
    }

    public MappingReferences(Set<MappingReference> mappingReferences, boolean restrictToDefinedMappings,
                             boolean forForgedMethods) {
        this.mappingReferences = mappingReferences;
        this.restrictToDefinedMappings = restrictToDefinedMappings;
        this.forForgedMethods = forForgedMethods;
    }

    public Set<MappingReference> getMappingReferences() {
        return mappingReferences;
    }

    public boolean isRestrictToDefinedMappings() {
        return restrictToDefinedMappings;
    }

    public boolean isForForgedMethods() {
        return forForgedMethods;
    }

    /**
     * @return all dependencies to other properties the contained mappings are dependent on
     */
    public Set<String> collectNestedDependsOn() {

        Set<String> nestedDependsOn = new LinkedHashSet<>();
        for ( MappingReference mapping : getMappingReferences() ) {
            nestedDependsOn.addAll( mapping.getMapping().getDependsOn() );
        }
        return nestedDependsOn;
    }

    /**
     * Check there are nested target references for this mapping options.
     *
     * @return boolean, true if there are nested target references
     */
    public boolean hasNestedTargetReferences() {

        for ( MappingReference mappingRef : mappingReferences ) {
            TargetReference targetReference = mappingRef.getTargetReference();
            if ( targetReference.isNested()) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( !( o instanceof MappingReferences ) ) {
            return false;
        }

        MappingReferences that = (MappingReferences) o;

        if ( restrictToDefinedMappings != that.restrictToDefinedMappings ) {
            return false;
        }
        if ( forForgedMethods != that.forForgedMethods ) {
            return false;
        }
        if ( !Objects.equals( mappingReferences, that.mappingReferences ) ) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return mappingReferences != null ? mappingReferences.hashCode() : 0;
    }

    /**
     * MapStruct filters automatically inversed invalid methods out. TODO: this is a principle we should discuss!
     * @param mappingRef
     * @return
     */
    private static boolean isValidWhenInversed(MappingReference mappingRef) {
        MappingOptions mapping = mappingRef.getMapping();
        if ( mapping.getInheritContext() != null && mapping.getInheritContext().isReversed() ) {
            return ( mappingRef.getSourceReference() == null ||
                mappingRef.getSourceReference().isValid() );
        }
        return true;
    }

}
