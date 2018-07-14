/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

import java.util.List;
import javax.lang.model.type.TypeMirror;

/**
 * Indicates that a type has too many builder creation methods.
 * This exception can be used to signal the MapStruct processor that more than one builder creation method was found.
 *
 * @author Filip Hrisafov
 */
public class MoreThanOneBuilderCreationMethodException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final TypeMirror type;
    private final List<BuilderInfo> builderCreationMethods;

    public MoreThanOneBuilderCreationMethodException(TypeMirror type, List<BuilderInfo> builderCreationMethods) {
        this.type = type;
        this.builderCreationMethods = builderCreationMethods;
    }

    public TypeMirror getType() {
        return type;
    }

    public List<BuilderInfo> getBuilderInfo() {
        return builderCreationMethods;
    }
}
