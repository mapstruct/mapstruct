/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.assignment;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem;
import org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem;
import org.mapstruct.ap.internal.model.common.Assignment;
import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.common.TypeFactory;

import static org.mapstruct.ap.internal.gem.NullValueCheckStrategyGem.ALWAYS;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.IGNORE;
import static org.mapstruct.ap.internal.gem.NullValuePropertyMappingStrategyGem.SET_TO_DEFAULT;
import static org.mapstruct.ap.internal.model.assignment.ExistingInstanceSetterWrapperForCollectionsAndMaps.NullValueMappingStrategy.MAP_NULL_TO_CLEAR;
import static org.mapstruct.ap.internal.model.assignment.ExistingInstanceSetterWrapperForCollectionsAndMaps.NullValueMappingStrategy.MAP_NULL_TO_DEFAULT;

/**
 * This wrapper handles the situation where an assignment is done for an update method.
 *
 * In case of a pre-existing target the wrapper checks if there is a collection or map initialized on the target bean
 * (not null). If so it uses the addAll (for collections) or putAll (for maps). The collection / map is cleared in case
 * of a pre-existing target {@link org.mapstruct.MappingTarget }before adding the source entries.
 *
 * If there is no pre-existing target, or the target Collection / Map is not initialized (null) the setter is used to
 * create a new Collection / Map with the copy constructor.
 *
 * @author Sjaak Derksen
 */
public class ExistingInstanceSetterWrapperForCollectionsAndMaps
    extends SetterWrapperForCollectionsAndMapsWithNullCheck {

    private final boolean includeElseBranch;
    private final NullValueMappingStrategy nullValueMappingStrategy;
    private final Type targetType;

    public ExistingInstanceSetterWrapperForCollectionsAndMaps(Assignment decoratedAssignment,
        List<Type> thrownTypesToExclude,
        Type targetType,
        NullValueCheckStrategyGem nvcs,
        NullValuePropertyMappingStrategyGem nvpms,
        TypeFactory typeFactory,
        boolean fieldAssignment) {

        super(
            decoratedAssignment,
            thrownTypesToExclude,
            targetType,
            typeFactory,
            fieldAssignment
        );
        this.nullValueMappingStrategy = mapNullValueMappingStrategy( nvpms );
        this.targetType = targetType;
        this.includeElseBranch = ALWAYS != nvcs && IGNORE != nvpms;
    }

    private static NullValueMappingStrategy mapNullValueMappingStrategy(NullValuePropertyMappingStrategyGem nvpms) {
        if ( nvpms == SET_TO_DEFAULT ) {
            return NullValueMappingStrategy.MAP_NULL_TO_DEFAULT;
        }
        else if ( nvpms == NullValuePropertyMappingStrategyGem.SET_TO_NULL ) {
            return NullValueMappingStrategy.MAP_NULL_TO_NULL;
        }
        else if ( nvpms == NullValuePropertyMappingStrategyGem.CLEAR ) {
            return NullValueMappingStrategy.MAP_NULL_TO_CLEAR;
        }
        return NullValueMappingStrategy.MAP_NULL_TO_DEFAULT;
    }

    @Override
    public Set<Type> getImportTypes() {
        Set<Type> imported = new HashSet<>( super.getImportTypes() );
        if ( needsImport() && ( targetType.getImplementationType() != null ) ) {
            imported.add( targetType.getImplementationType() );
        }
        return imported;
    }

    public boolean isIncludeElseBranch() {
        return includeElseBranch;
    }

    public boolean isMapNullToDefault() {
        return nullValueMappingStrategy == MAP_NULL_TO_DEFAULT;
    }

    public boolean isMapNullToClear() {
        return nullValueMappingStrategy == MAP_NULL_TO_CLEAR;
    }

    private boolean needsImport() {
        return nullValueMappingStrategy == MAP_NULL_TO_DEFAULT || nullValueMappingStrategy == MAP_NULL_TO_CLEAR;
    }

    public enum NullValueMappingStrategy {
        MAP_NULL_TO_NULL,
        MAP_NULL_TO_DEFAULT,
        MAP_NULL_TO_CLEAR
    }

}
