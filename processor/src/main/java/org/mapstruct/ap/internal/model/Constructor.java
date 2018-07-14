/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * Basic interface class that facilitates an empty constructor.
 *
 * @author Sjaak Derksen
 */
public interface Constructor {

    String getName();

    Set<Type> getImportTypes();

}
