/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.source.selector;

import java.util.List;

import org.mapstruct.ap.internal.model.common.ParameterBinding;
import org.mapstruct.ap.internal.model.source.Method;

/**
 * A selected method with additional metadata that might be required for further usage of the selected method.
 *
 * @author Andreas Gudian
 */
public class SelectedMethod<T extends Method> {
    private T method;
    private List<ParameterBinding> parameterBindings;

    public SelectedMethod(T method) {
        this.method = method;
    }

    public T getMethod() {
        return method;
    }

    public List<ParameterBinding> getParameterBindings() {
        return parameterBindings;
    }

    public void setParameterBindings(List<ParameterBinding> parameterBindings) {
        this.parameterBindings = parameterBindings;
    }

    @Override
    public String toString() {
        return method.toString();
    }
}
