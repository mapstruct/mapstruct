/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.source.Method;

/**
 * A method of a decorator which delegates to the corresponding method of the generated mapper implementation.
 *
 * @author Gunnar Morling
 */
public class DelegatingMethod extends MappingMethod {

    public DelegatingMethod(Method method) {
        super( method );
    }
}
