/**
 *  Copyright 2012-2013 Gunnar Morling (http://www.gunnarmorling.de/)
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

import org.mapstruct.ap.util.Collections;

/**
 * Mapper reference which is retrieved via Spring-based dependency injection.
 * method. Used if "spring" is specified as component model via
 * {@code Mapper#uses()}.
 *
 * @author Gunnar Morling
 * @author Andreas Gudian
 */
public class SpringMapperReference extends AbstractModelElement implements MapperReference {

    private Type type;

    public SpringMapperReference(Type type) {
        this.type = type;
    }

    @Override
    public Type getMapperType() {
        return type;
    }

    @Override
    public Set<Type> getImportTypes() {
        return Collections.asSet( type, new Type( "org.springframework.beans.factory.annotation", "Autowired" ) );
    }
}
