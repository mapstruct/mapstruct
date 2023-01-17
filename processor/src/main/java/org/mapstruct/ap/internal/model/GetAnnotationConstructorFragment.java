/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model;

import org.mapstruct.ap.internal.model.common.ConstructorFragment;
import org.mapstruct.ap.internal.model.common.Type;

public class GetAnnotationConstructorFragment implements ConstructorFragment {

    private final Type sourceType;
    private final String methodName;
    private final Type annotationClass;
    private final String reflectionMethodName;

    public GetAnnotationConstructorFragment(Type sourceType, String methodName, Type annotationClass,
                                            String reflectionMethodName) {
        this.sourceType = sourceType;
        this.methodName = methodName;
        this.annotationClass = annotationClass;
        this.reflectionMethodName = reflectionMethodName;
    }

    public Type getSourceType() {
        return sourceType;
    }

    public String getMethodName() {
        return methodName;
    }

    public Type getAnnotationClass() {
        return annotationClass;
    }

    public String getReflectionMethodName() {
        return reflectionMethodName;
    }

}
