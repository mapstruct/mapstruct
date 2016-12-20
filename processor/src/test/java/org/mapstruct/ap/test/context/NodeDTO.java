/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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

import java.util.List;

/**
 * @author Andreas Gudian
 */
public class NodeDTO {
    private NodeDTO parent;

    private int id;
    private String name;

    private List<NodeDTO> children;
    private List<AttributeDTO> attributes;

    public NodeDTO(int id) {
        this.id = id;
    }

    public NodeDTO getParent() {
        return parent;
    }

    public void setParent(NodeDTO parent) {
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

    public List<NodeDTO> getChildren() {
        return children;
    }

    public void setChildren(List<NodeDTO> children) {
        this.children = children;
    }

    public List<AttributeDTO> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AttributeDTO> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "NodeDTO [name=" + name + "]";
    }

    public static class AttributeDTO {
        private NodeDTO node;

        private String name;
        private String value;

        public NodeDTO getNode() {
            return node;
        }

        public void setNode(NodeDTO node) {
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
            return "AttributeDTO [name=" + name + ", value=" + value + "]";
        }
    }
}
