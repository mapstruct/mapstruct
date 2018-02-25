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
package org.mapstruct.ap.spi;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

/**
 * Indicates a type was visited whose hierarchy was erroneous, because it has a non-existing super-type.
 * <p>
 * This exception can be used to signal the MapStruct processor to postpone the generation of the mappers to the next
 * round
 *
 * @author Gunnar Morling
 */
public class TypeHierarchyErroneousException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private final TypeMirror type;

    public TypeHierarchyErroneousException(TypeElement element) {
        this( element.asType() );
    }

    public TypeHierarchyErroneousException(TypeMirror type) {
        this.type = type;
    }

    public TypeMirror getType() {
        return type;
    }
}
