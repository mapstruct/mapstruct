/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import java.util.List;

/**
 * @author Pascal Grün
 */
public class NodeDto {

    private NodeDto parent;
    private String name;
    private List<NodeDto> children;
    private List<AttributeDto> attributes;

    public NodeDto getParent() {
        return parent;
    }

    public void setParent(NodeDto parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<NodeDto> getChildren() {
        return children;
    }

    public void setChildren(List<NodeDto> children) {
        this.children = children;
    }

    public List<AttributeDto> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDto> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "NodeDto [name=" + name + "]";
    }
}
