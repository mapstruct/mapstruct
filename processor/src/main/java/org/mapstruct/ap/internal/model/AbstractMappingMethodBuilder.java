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

package org.mapstruct.ap.internal.model;

/**
 * An abstract builder that can be reused for building {@link MappingMethod}(s).
 *
 * @author Filip Hrisafov
 */
public abstract class AbstractMappingMethodBuilder<B extends AbstractMappingMethodBuilder<B, M>,
    M extends MappingMethod> {

    protected B myself;
    protected MappingBuilderContext ctx;

    public AbstractMappingMethodBuilder(Class<B> selfType) {
        myself = selfType.cast( this );
    }

    public B mappingContext(MappingBuilderContext mappingContext) {
        this.ctx = mappingContext;
        return myself;
    }

    public abstract M build();
}
