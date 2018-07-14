/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

/**
 * @author Pascal Grün
 */
public class AttributeDto {

    private NodeDto node;
    private String name;
    private String value;

    public NodeDto getNode() {
        return node;
    }

    public void setNode(NodeDto node) {
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
        return "AttributeDto [name=" + name + ", value=" + value + "]";
    }
}
