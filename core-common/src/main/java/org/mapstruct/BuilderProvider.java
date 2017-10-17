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

public enum BuilderProvider {
    /**
     * https://projectlombok.org/features/Builder
     */
    LOMBOK,

    /**
     * https://github.com/google/auto/tree/master/value
     */
    AUTOVALUE,
    /**
     * https://immutables.github.io/
     */
    IMMUTABLES,

    /**
     * This approach will look on the classpath for the existence of {@link #LOMBOK}, {@link #AUTOVALUE}, or
     * {@link #IMMUTABLES}
     */
    AUTODETECT,

    /**
     * The default provider looks for an SPI implementation - if none is found, Mapstruct will use a convention-based
     * default implementation.
     */
    DEFAULT;
}
