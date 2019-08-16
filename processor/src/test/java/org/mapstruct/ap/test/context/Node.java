/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andreas Gudian
 */
public class Node {
    private Node parent;

    private String name;

    private List<Node> children;
    private List<Attribute> attributes;

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<>();
        this.attributes = new ArrayList<>();
    }

    public Node getParent() {
        return parent;
    }

    public String getName() {
        return name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void addChild(Node node) {
        children.add( node );
        node.parent = this;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add( attribute );
        attribute.setNode( this );
    }

    public static class Attribute {
        private Node node;

        private String name;
        private String value;
        private int magicNumber;

        public Attribute(String name, String value, int magicNumber) {
            this.name = name;
            this.value = value;
            this.magicNumber = magicNumber;
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

        public String getValue() {
            return value;
        }

        public int getMagicNumber() {
            return magicNumber;
        }
    }
}
