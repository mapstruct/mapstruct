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
package org.mapstruct;

/**
 * Strategy to decide whether we should check null or hasX method before mapping
 *
 * @author Sean Huang
 */
public enum SourceValuePresenceCheckStrategy {

    /**
     * Check != null for inline conversions)
     *
     */
    IS_NULL_INLINE,

    /**
     * Always check != null, no matter whether it's an inline or method conversion
     *
     */
    IS_NULL,

    /**
     * Will invoke custom hasX() method, before mapping,
     * name to be given through the accessor naming strategy
     */
    CUSTOM;
}
