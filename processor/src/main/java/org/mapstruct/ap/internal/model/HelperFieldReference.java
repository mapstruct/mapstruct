/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.Type;

/**
 * reference used by HelperMethod to create an additional field in the mapper.
 *
 * @author Ewald Volkert
 */
public interface HelperFieldReference {

    /**
     * @return variable name of the field
     */
    String getVariableName();

    /**
     * @return type of the field
     */
    Type getType();

}
