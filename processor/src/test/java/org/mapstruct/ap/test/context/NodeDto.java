/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import java.util.List;

/**
 * @author Andreas Gudian
 */
public class NodeDto {
    private NodeDto parent;

    private int id;
    private String name;

    private List<NodeDto> children;
    private List<AttributeDto> attributes;

    public NodeDto(int id) {
        this.id = id;
    }

    public NodeDto getParent() {
        return parent;
    }

    public void setParent(NodeDto parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
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

    public static class AttributeDto {
        private NodeDto node;

        private String name;
        private String value;
        private int magicNumber;

        public AttributeDto(int magicNumber) {
            this.magicNumber = magicNumber;
        }

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

        public int getMagicNumber() {
            return magicNumber;
        }
    }
}
