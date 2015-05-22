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
     * Set the default {@link AccessorNamingStrategy} implementation. Custom implementations may use it to
     * keep parts of the default behavior. This method is invoked before any other method of the interface.
     * <p>
     * Default implementation: Does nothing.
     *
     * @param defaultAccessorNamingStrategy The default implementation.
     */
    void setDefaultAccessorNamingStrategy(AccessorNamingStrategy defaultAccessorNamingStrategy);

    /**
     * Determine if a method name defines a getter with a non-boolean return type.
     * <p>
     * Default implementation: Method name starts with "get" and is longer than 3 characters.
     *
     * @param methodName The method name.
     * @return {@code true} if the method name can be a non-boolean getter, {@code false} otherwise.
     */
    boolean isNonBooleanGetterName(String methodName);

    /**
     * Determine if a method name defines a getter with a boolean return type.
     * <p>
     * Default implementation: Method name starts with "is" and is longer than 2 characters.
     *
     * @param methodName The method name.
     * @return {@code true} if the method name can be a boolean getter, {@code false} otherwise.
     */
    boolean isBooleanGetterName(String methodName);

    /**
     * Determine if a method name defines a setter.
     * <p>
     * Default implementation: Method name starts with "set" and is longer than 3 characters.
     *
     * @param methodName The method name.
     * @return {@code true} if the method name can be a setter, {@code false} otherwise.
     */
    boolean isSetterName(String methodName);

    /**
     * Determine if a method name defines an adder.
     * <p>
     * Default implementation: Method name starts with "add" and is longer than 3 characters.
     *
     * @param methodName The method name.
     * @return {@code true} if the method name can be an adder, {@code false} otherwise.
     */
    boolean isAdderName(String methodName);

    /**
     * Extract the property name from a method name for which {@link #isNonBooleanGetterName(String)} returned
     * {@code true}.
     * <p>
     * Default implementation: Remove first 3 characters ("get") of the method name, and
     * {@link java.beans.Introspector#decapitalize(String) decapitalize} the result.
     *
     * @param methodName The method name, guaranteed to be a non-boolean getter name.
     * @return The property name corresponding to the method name.
     */
    String getPropertyNameForNonBooleanGetterName(String methodName);

    /**
     * Extract the property name from a method name for which {@link #isBooleanGetterName(String)} returned
     * {@code true}.
     * <p>
     * Default implementation: Remove the first 2 characters ("is") of the method name, and
     * {@link java.beans.Introspector#decapitalize(String) decapitalize} the result.
     *
     * @param methodName The method name, guaranteed to be a boolean getter name.
     * @return The property name corresponding to the method name.
     */
    String getPropertyNameForBooleanGetterName(String methodName);

    /**
     * Extract the property name from a method name for which {@link #isSetterName(String)} returned {@code true}.
     * <p>
     * Default implementation: Remove the first 3 characters ("set") of the method name, and
     * {@link java.beans.Introspector#decapitalize(String) decapitalize} the result.
     *
     * @param methodName The method name, guaranteed to be a setter name.
     * @return The property name corresponding to the method name.
     */
    String getPropertyNameForSetterName(String methodName);

    /**
     * Extract the element name (singular form of the collection's property name) from a method name for which
     * {@link #isAdderName(String)} returned {@code true}.
     * <p>
     * Default implementation: Remove the first 3 characters ("add") of the method name, and
     * {@link java.beans.Introspector#decapitalize(String) decapitalize} the result.
     *
     * @param methodName The method name, guaranteed to be an adder name.
     * @return The element name corresponding to the method name.
     */
    String getElementNameForAdderName(String methodName);

    /**
     * Extract the non-boolean getter method name from the setter method name of the same property.
     * <p>
     * Default implementation: Replace the first 3 characters ("get") of the method name with "set".
     *
     * @param methodName The method name, guaranteed to be a setter name.
     * @return The corresponding non-boolean getter method name.
     */
    String getNonBooleanGetterNameForSetterName(String methodName);
}
