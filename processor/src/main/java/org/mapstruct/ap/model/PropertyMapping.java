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

import java.util.Set;

import org.mapstruct.ap.model.common.ModelElement;
import org.mapstruct.ap.model.common.Type;


/**
 * Represents the mapping between a source and target property, e.g. from
 * {@code String Source#foo} to {@code int Target#bar}. Name and type of source
 * and target property can differ. If they have different types, the mapping
 * must either refer to a mapping method or a conversion.
 *
 * @author Gunnar Morling
 */
public class PropertyMapping extends ModelElement {

    private final String sourceBeanName;

    private final String targetAccessorName;
    private final Type targetType;

    private final Assignment assignment;

    // Constructor for creating mappings of constant expressions.
    public PropertyMapping(String targetAccessorName, Type targetType, Assignment propertyAssignment) {
        this( null, targetAccessorName, targetType, propertyAssignment );
    }

    public PropertyMapping(String sourceBeanName, String targetAccessorName, Type targetType, Assignment assignment) {

        this.sourceBeanName = sourceBeanName;

        this.targetAccessorName = targetAccessorName;
        this.targetType = targetType;

        this.assignment = assignment;
    }

    public String getSourceBeanName() {
        return sourceBeanName;
    }

    public String getTargetAccessorName() {
        return targetAccessorName;
    }

    public Type getTargetType() {
        return targetType;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    @Override
    public Set<Type> getImportTypes() {
        return assignment.getImportTypes();
    }

    @Override
    public String toString() {
        return "PropertyMapping {" +
            "\n    targetName='" + targetAccessorName + "\'," +
            "\n    targetType=" + targetType + "," +
            "\n    propertyAssignment=" + assignment +
            "\n}";
    }
}
