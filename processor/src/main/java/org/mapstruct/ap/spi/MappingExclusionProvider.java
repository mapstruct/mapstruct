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

/**
 * A service provider interface that is used to control if MapStruct is allowed to generate automatic sub-mapping for
 * a given {@link TypeElement}.
 *
 * @author Filip Hrisafov
 * @since 1.2
 */
public interface MappingExclusionProvider {

    /**
     * Checks if MapStruct should not generate an automatic sub-mapping for the provided {@link TypeElement}, i.e.
     * MapStruct will not try to descent into this class and won't try to automatically map it with some other type.
     * The given {@code typeElement} will be excluded from the automatic sub-mapping generation
     *
     * @param typeElement that needs to be checked
     *
     * @return {@code true} if MapStruct should exclude the provided {@link TypeElement} from an automatic sub-mapping
     */
    boolean isExcluded(TypeElement typeElement);
}
