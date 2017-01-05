/**
 *  Copyright 2012-2017 Gunnar Morling (http://www.gunnarmorling.de/)
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
        this.children = new ArrayList<Node>();
        this.attributes = new ArrayList<Attribute>();
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
