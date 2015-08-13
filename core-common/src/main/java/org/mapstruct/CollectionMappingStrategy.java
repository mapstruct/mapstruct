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
 * Strategy for propagating the value of collection-typed properties from source to target.
 *
 * @author Sjaak Derksen
 */
public enum CollectionMappingStrategy {

    /**
     * The setter of the target property will be used to propagate the value:
     * {@code orderDto.setOrderLines(order.getOrderLines)}.
     * <p>
     * If no setter is available but a getter method, this will be used, under the assumption it has been initialized:
     * {@code orderDto.getOrderLines().addAll(order.getOrderLines)}.
     */
    ACCESSOR_ONLY,

    /**
     * If present, the setter of the target property will be used to propagate the value:
     * {@code orderDto.setOrderLines(order.getOrderLines)}.
     * <p>
     * If no setter but and adder method is present, that adder will be invoked for each element of the source
     * collection: {@code order.addOrderLine(orderLine() )}.
     * <p>
     * If neither a setter nor an adder method but a getter for the target property is present, that getter will be
     * used, assuming it returns an initialized collection: If no setter is available, MapStruct will first look for an
     * adder method before resorting to a getter.
     */
    SETTER_PREFERRED,

    /**
     * Identical to {@link #SETTER_PREFERRED}, only that adder methods will be preferred over setter methods, if both
     * are present for a given collection-typed property.
     */
    ADDER_PREFERRED;
}
