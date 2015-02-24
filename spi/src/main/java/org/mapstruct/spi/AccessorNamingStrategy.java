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
package org.mapstruct.spi;

/**
 * A service provider interface for the mapping between method names and properties.
 *
 * @author Christian Schuster
 */
public interface AccessorNamingStrategy {

    /**
     * Determine if a method name defines a getter with a non-boolean return type.
     *
     * @param methodName The method name.
     * @return <code>true</code> if the method name can be a non-boolean getter, <code>false</code> otherwise.
     */
    boolean isNonBooleanGetterName(String methodName);

    /**
     * Determine if a method name defines a getter with a boolean return type.
     *
     * @param methodName The method name.
     * @return <code>true</code> if the method name can be a boolean getter, <code>false</code> otherwise.
     */
    boolean isBooleanGetterName(String methodName);

    /**
     * Determine if a method name defines a setter.
     *
     * @param methodName The method name.
     * @return <code>true</code> if the method name can be a setter, <code>false</code> otherwise.
     */
    boolean isSetterName(String methodName);

    /**
     * Determine if a method name defines an adder.
     *
     * @param methodName The method name.
     * @return <code>true</code> if the method name can be an adder, <code>false</code> otherwise.
     */
    boolean isAdderName(String methodName);

    /**
     * Extract the property name from a method name for which {@link #isNonBooleanGetterName(String)} returned
     * <code>true</code>.
     *
     * @param methodName The method name, guaranteed to be a non-boolean getter name.
     * @return The property name corresponding to the method name.
     */
    String getPropertyNameForNonBooleanGetterName(String methodName);

    /**
     * Extract the property name from a method name for which {@link #isBooleanGetterName(String)} returned
     * <code>true</code>.
     *
     * @param methodName The method name, guaranteed to be a boolean getter name.
     * @return The property name corresponding to the method name.
     */
    String getPropertyNameForBooleanGetterName(String methodName);

    /**
     * Extract the property name from a method name for which {@link #isSetterName(String)} returned <code>true</code>.
     *
     * @param methodName The method name, guaranteed to be a setter name.
     * @return The property name corresponding to the method name.
     */
    String getPropertyNameForSetterName(String methodName);

    /**
     * Extract the element name (singular form of the collection's property name) from a method name for which
     * {@link #isAdderName(String)} returned <code>true</code>.
     *
     * @param methodName The method name, guaranteed to be an adder name.
     * @return The element name corresponding to the method name.
     */
    String getElementNameForAdderName(String methodName);

    /**
     * Extract the non-boolean getter method name from the setter method name of the same property.
     *
     * @param methodName The method name, guaranteed to be a setter name.
     * @return The corresponding non-boolean getter method name.
     */
    String getNonBooleanGetterNameForSetterName(String methodName);
}
