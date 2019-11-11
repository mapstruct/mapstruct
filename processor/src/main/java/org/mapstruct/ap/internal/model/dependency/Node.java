/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.dependency;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        descendants = new ArrayList<>();
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

        if ( !Objects.equals( name, other.name ) ) {
            return false;
        }

        return true;
    }
}
