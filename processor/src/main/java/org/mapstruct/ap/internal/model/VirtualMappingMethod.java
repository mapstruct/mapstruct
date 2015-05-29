/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
package org.mapstruct.ap.internal.model;

import java.util.Set;

import org.mapstruct.ap.internal.model.common.Type;
import org.mapstruct.ap.internal.model.source.builtin.BuiltInMethod;

/**
 * A mapping method which is not based on an actual method declared in the original mapper interface but is added as
 * private method to map a certain source/target type combination. Based on a {@link BuiltInMethod}.
 *
 * @author Gunnar Morling
 */
public class VirtualMappingMethod extends MappingMethod {

    private final String templateName;
    private final Set<Type> importTypes;

    public VirtualMappingMethod(BuiltInMethod method) {
        super( method );
        this.importTypes = method.getImportTypes();
        this.templateName = getTemplateNameForClass( method.getClass() );
    }

    @Override
    public String getTemplateName() {
        return templateName;
    }

    @Override
    public Set<Type> getImportTypes() {
        return importTypes;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( templateName == null ) ? 0 : templateName.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        VirtualMappingMethod other = (VirtualMappingMethod) obj;
        if ( templateName == null ) {
            if ( other.templateName != null ) {
                return false;
            }
        }
        else if ( !templateName.equals( other.templateName ) ) {
            return false;
        }
        return true;
    }
}
