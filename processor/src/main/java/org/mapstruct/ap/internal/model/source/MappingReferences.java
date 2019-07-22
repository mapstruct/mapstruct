/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import org.mapstruct.ap.internal.model.common.TypeFactory;
import org.mapstruct.ap.internal.util.FormattingMessager;

public class MappingReferences {

    private static final MappingReferences EMPTY = new MappingReferences( Collections.emptySet(), false );

    private final Set<MappingReference> mappingReferences;
    private final boolean restrictToDefinedMappings;
    private final boolean forForgedMethods;

    public static MappingReferences empty() {
        return EMPTY;
    }

    public static MappingReferences forSourceMethod(SourceMethod sourceMethod, FormattingMessager messager,
                                                    TypeFactory typeFactory) {

        Set<Mapping> mappings = sourceMethod.getMappingOptions().getMappings();


        Set<MappingReference> references = new LinkedHashSet<>();
        for ( Mapping mapping : sourceMethod.getMappingOptions().getMappings() ) {

            // handle source reference
            SourceReference sourceReference = new SourceReference.BuilderFromMapping().mapping( mapping )
                                                                                      .method( sourceMethod )
                                                                                      .messager( messager )
                                                                                      .typeFactory( typeFactory )
                                                                                      .build();

            // handle target reference
            TargetReference targetReference = new TargetReference.BuilderFromTargetMapping().mapping( mapping )
                                                                                            .method( sourceMethod )
                                                                                            .messager( messager )
                                                                                            .typeFactory( typeFactory )
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
            if ( targetReference.isValid() && targetReference.getPropertyEntries().size() > 1 ) {
                return true;
            }

        }
        return false;
    }


    /**
     * MapStruct filters automatically inversed invalid methods out. TODO: this is a principle we should discuss!
     * @param mappingRef
     * @return
     */
    private static boolean isValidWhenInversed(MappingReference mappingRef) {
        Mapping mapping = mappingRef.getMapping();
        if ( mapping.getInheritContext() != null && mapping.getInheritContext().isReversed() ) {
            return mappingRef.getTargetReference().isValid() && ( mappingRef.getSourceReference() == null ||
                mappingRef.getSourceReference().isValid() );
        }
        return true;
    }
}
