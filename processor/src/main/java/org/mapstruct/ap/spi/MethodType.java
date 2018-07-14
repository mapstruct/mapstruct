/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.spi;

/**
 * Different types of a method.
 *
 * @author Gunnar Morling
 */
public enum MethodType {

    /**
     * A JavaBeans getter method, e.g. {@code public String getName()}.
     */
    GETTER,

    /**
     * A JavaBeans setter method, e.g. {@code public void setName(String name)}.
     */
    SETTER,

    /**
     * An adder method, e.g. {@code public void addItem(String item)}.
     */
    ADDER,

    /**
     * Any method which is neither a JavaBeans getter, setter nor an adder method.
     */
    OTHER,

    /**
     * A method to check whether a property is present, e.g. {@code public String hasName()}.
     */
    PRESENCE_CHECKER;
}
