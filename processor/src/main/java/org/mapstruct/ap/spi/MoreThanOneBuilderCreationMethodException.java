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
