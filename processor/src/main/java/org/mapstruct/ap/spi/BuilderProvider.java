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

import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

/**
 * A service provider interface that is used to detect types that require a builder for mapping.  This interface could
 * support automatic detection of builders for projects like Lombok, Immutables, AutoValue, etc.
 * @author Filip Hrisafov
 */
public interface BuilderProvider {

    /**
     * Find the builder information, if any, for the {@code type}.
     *
     * @param type the type for which a builder should be found
     * @param elements the util elements that can be used for operations on program elements
     * @param types the util types that can be used for operations on {@link TypeMirror}(s)
     *
     * @return the builder info for the {@code type} if it exists, or {@code null} if there is no builder
     *
     * @throws TypeHierarchyErroneousException if the type that needs to be visited is not ready yet, this signals the
     * MapStruct processor to postpone the generation of the mappers to the next round
     */
    BuilderInfo findBuilderInfo(TypeMirror type, Elements elements, Types types);
}
