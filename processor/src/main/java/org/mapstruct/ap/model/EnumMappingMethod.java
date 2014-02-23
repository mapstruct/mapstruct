/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.model;

import java.util.List;

import org.mapstruct.ap.model.common.Parameter;
import org.mapstruct.ap.model.source.EnumMapping;
import org.mapstruct.ap.model.source.Method;

/**
 * A {@link MappingMethod} which maps one enum type to another, optionally configured by one or more
 * {@link EnumMapping}s.
 *
 * @author Gunnar Morling
 */
public class EnumMappingMethod extends MappingMethod {

    private final List<EnumMapping> enumMappings;

    public EnumMappingMethod(Method method, List<EnumMapping> enumMappings) {
        super( method );
        this.enumMappings = enumMappings;
    }

    public List<EnumMapping> getEnumMappings() {
        return enumMappings;
    }

    public Parameter getSourceParameter() {
        return getParameters().iterator().next();
    }
}
