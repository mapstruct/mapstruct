/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

/**
 * @author Pascal Grün
 */
public class Attribute {
    private Node node;

    private String name;
    private String value;

    public Attribute() {
        // default constructor for MapStruct
    }

    public Attribute(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Attribute [name=" + name + ", value=" + value + "]";
    }
}
