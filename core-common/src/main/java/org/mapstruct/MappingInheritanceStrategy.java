/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
 * Strategy for inheriting configurations given for methods of prototype mapping methods (declared on mapper config
 * classes) to actual mapping methods declared on mappers referring to such config class via {@link Mapper#config()}.
 *
 * @author Andreas Gudian
 */
public enum MappingInheritanceStrategy {
    /**
     * Apply the method-level configuration annotations only if the prototype method is explicitly referenced using
     * {@link InheritConfiguration}.
     */
    EXPLICIT,

    /**
     * Inherit the method-level configuration annotations automatically if source and target types of the prototype
     * method are assignable from the types of a given mapping method.
     */
    AUTO_INHERIT_FROM_CONFIG;
}
