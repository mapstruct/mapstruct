/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
 *  and/or other contributors as indicated by the @authors tag. See the
 *  copyright.txt file in the distribution for a full listing of all
 *  contributors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
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
