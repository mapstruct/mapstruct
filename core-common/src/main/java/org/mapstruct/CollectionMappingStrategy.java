/**
 *  Copyright 2012-2014 Gunnar Morling (http://www.gunnarmorling.de/)
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
 *  Strategy for mapping of collections.
 * @author Sjaak Derksen
 */
public enum CollectionMappingStrategy {

    /**
     * MapStruct will consider setter methods as target as way to access the target.
     *
     * Note: If no setter is available a getter will be used under the assumption it has been initialized.
     */
    SETTER_ONLY,
    /**
     * MapStruct will consider setter methods as preferred way to access the target.
     *
     * If no setter is available, MapStruct will first look for an adder method before resorting to a getter.
     */
    SETTER_PREFERRED,
    /**
     * MapStruct will consider adder methods as preferred way to access the target.
     *
     * If no adder is available, MapStruct will first look for a setter method before resorting to a getter.
     */
    ADDER_PREFERRED,
    /**
     * The default option is: {@link CollectionMappingStrategy#SETTER_ONLY}.
     *
     * The default options forces deliberate setting in {@link Mapper#collectionMappingStrategy() }, in order
     * to override a setting in {@link MapperConfig#collectionMappingStrategy() }
     */
    DEFAULT;
}
