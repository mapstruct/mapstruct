/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import javax.lang.model.type.TypeMirror;

/**
 * A service provider interface that is used to detect types that require a builder for mapping.  This interface could
 * support automatic detection of builders for projects like Lombok, Immutables, AutoValue, etc.
 * @author Filip Hrisafov
 *
 * @since 1.3
 */
public interface BuilderProvider {

    /**
     * Initializes the builder provider with the MapStruct processing environment.
     *
     * @param processingEnvironment environment for facilities
     */
    default void init(MapStructProcessingEnvironment processingEnvironment) {

    }

    /**
     * Find the builder information, if any, for the {@code type}.
     *
     * @param type the type for which a builder should be found
     * @return the builder info for the {@code type} if it exists, or {@code null} if there is no builder
     *
     * @throws TypeHierarchyErroneousException if the type that needs to be visited is not ready yet, this signals the
     * MapStruct processor to postpone the generation of the mappers to the next round
     * @throws MoreThanOneBuilderCreationMethodException if {@code type} has more than one method that can create the
     * builder
     */
    BuilderInfo findBuilderInfo(TypeMirror type);
}
