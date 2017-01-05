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
package org.mapstruct.ap.internal.model.dependency;

import java.util.ArrayList;
import java.util.List;

/**
 * A node of a directed graph.
 *
 * @author Gunnar Morling
 */
class Node {

    private final String name;
    private boolean visited;
    private int traversalSequence = -1;

    /**
     * The direct descendants of this node.
     */
    private final List<Node> descendants;

    Node(String name) {
        this.name = name;
        descendants = new ArrayList<Node>();
    }

    public String getName() {
        return name;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isProcessed() {
        return traversalSequence >= 0;
    }

    public int getTraversalSequence() {
        return traversalSequence;
    }

    public void setTraversalSequence(int traversalSequence) {
        this.traversalSequence = traversalSequence;
    }

    public void addDescendant(Node node) {
        descendants.add( node );
    }

    public List<Node> getDescendants() {
        return descendants;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ( ( name == null ) ? 0 : name.hashCode() );
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( getClass() != obj.getClass() ) {
            return false;
        }
        Node other = (Node) obj;
        if ( name == null ) {
            if ( other.name != null ) {
                return false;
            }
        }
        else if ( !name.equals( other.name ) ) {
            return false;
        }
        return true;
    }
}
