/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Pascal Grün
 */
public class Node {
    private Node parent;

    private String name;

    private List<Node> children;
    private List<Attribute> attributes;

    public Node() {
        // default constructor for MapStruct
    }

    public Node(String name) {
        this.name = name;
        this.children = new ArrayList<Node>();
        this.attributes = new ArrayList<Attribute>();
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void addChild(Node node) {
        children.add( node );
        node.setParent( this );
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void addAttribute(Attribute attribute) {
        attributes.add( attribute );
        attribute.setNode( this );
    }

    @Override
    public String toString() {
        return "Node [name=" + name + "]";
    }
}
