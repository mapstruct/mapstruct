/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.common;

import java.util.Map;

/**
 * reference used by BuiltInMethod/HelperMethod to create an additional field in the mapper.
 */
public interface FieldReference {

    /**
     *
     * @return variable name of the field
     */
    String getVariableName();

    /**
     *
     * @return type of the field
     */
    Type getType();

    /**
     * @return additional template parameters
     */
    default Map<String, Object> getTemplateParameter() {
        return null;
    }
}
