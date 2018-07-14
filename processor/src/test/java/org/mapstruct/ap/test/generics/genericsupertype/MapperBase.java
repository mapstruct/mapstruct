/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.generics.genericsupertype;

/**
 * @author Gunnar Morling
 */
public abstract class MapperBase<S, T> {

    public abstract VesselDto vesselToDto(Vessel vessel);
}
