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
package org.mapstruct;

/**
 * Strategy for dealing with null source values.
 *
 * <b>Note:</b> This strategy is not in effect when the a specific source presence check method is defined
 * in the service provider interface (SPI).
 *
 * @author Sean Huang
 */
public enum NullValueCheckStrategy {

    /**
     * This option includes a null check. When:
     * <br>
     * <br>
     * <ol>
     *   <li>a source value is directly assigned to a target</li>
     *   <li>a source value assigned to a target by calling a type conversion on the target first</li>
     * </ol>
     * <br>
     * <b>NOTE:</b> mapping methods (generated or hand written) are excluded from this null check. They are intended to
     * handle a null source value as 'valid' input.
     *
     */
    ON_IMPLICIT_CONVERSION,

    /**
     * This option always includes a null check.
     */
    ALWAYS,

}
