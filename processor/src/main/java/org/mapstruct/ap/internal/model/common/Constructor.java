/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model.common;

import javax.lang.model.element.ExecutableElement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author Ciaran Liedeman
 */
public class Constructor {

    // Used to preserve property order
    private final LinkedHashMap<String, Parameter> properties;
    private final ExecutableElement executableElement;

    public Constructor(LinkedHashMap<String, Parameter> properties, final ExecutableElement executableElement) {
        this.properties = properties;
        this.executableElement = executableElement;
    }

    public List<String> getPropertyNames() {
        return new ArrayList<String>( properties.keySet() );
    }

    public Parameter getParameter(String property) {
        return properties.get( property );
    }

    public ExecutableElement getExecutableElement() {
        return executableElement;
    }

    public boolean hasProperty(String property) {
        return properties.keySet().contains( property );
    }

}
