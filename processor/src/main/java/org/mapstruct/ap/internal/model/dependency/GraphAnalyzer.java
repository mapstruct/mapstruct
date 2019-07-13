/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.internal.model.dependency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

/**
 * Analyzes graphs: Discovers all descendants of given nodes and detects cyclic dependencies between nodes if present.
 *
 * @author Gunnar Morling
 */
public class GraphAnalyzer {

    private final Map<String, Node> nodes;
    private final Set<List<String>> cycles;
    private final Stack<Node> currentPath;
    private int nextTraversalSequence = 0;

    private GraphAnalyzer(Map<String, Node> nodes) {
        this.nodes = nodes;
        cycles = new HashSet<>();
        currentPath = new Stack<>();
    }

    public static GraphAnalyzerBuilder builder() {
        return new GraphAnalyzerBuilder();
    }

    public static GraphAnalyzerBuilder withNode(String name, String... descendants) {
        return builder().withNode( name, descendants );
    }

    /**
     * Performs a full traversal of the graph, detecting potential cycles and calculates the full list of descendants of
     * the nodes.
     */
    private void analyze() {
        for ( Node node : nodes.values() ) {
            depthFirstSearch( node );
        }
    }

    /**
     * Returns the traversal sequence number of the given node. The ascending order of the traversal sequence numbers of
     * multiple nodes represents the depth-first traversal order of those nodes.
     * <p>
     * <b>Note</b>: The traversal sequence numbers will only be complete if the graph contains no cycles.
     *
     * @param name the node name to get the traversal sequence number for
     * @return the traversal sequence number, or {@code -1} if the node doesn't exist or the node was not visited (in
     *         case of cycles).
     */
    public int getTraversalSequence(String name) {
        Node node = nodes.get( name );
        return node != null ? node.getTraversalSequence() : -1;
    }

    public Set<List<String>> getCycles() {
        return cycles;
    }

    private void depthFirstSearch(Node node) {
        if ( node.isProcessed() ) {
            return;
        }

        currentPath.push( node );

        // the node is on the stack already -> cycle
        if ( node.isVisited() ) {
            cycles.add( getCurrentCycle( node ) );
            currentPath.pop();
            return;
        }

        node.setVisited( true );

        for ( Node descendant : node.getDescendants() ) {
            depthFirstSearch( descendant );
        }

        node.setTraversalSequence( nextTraversalSequence++ );
        currentPath.pop();
    }

    private List<String> getCurrentCycle(Node start) {
        List<String> cycle = new ArrayList<>();
        boolean inCycle = false;

        for ( Node n : currentPath ) {
            if ( !inCycle && n.equals( start ) ) {
                inCycle = true;
            }

            if ( inCycle ) {
                cycle.add( n.getName() );
            }
        }

        return cycle;
    }

    public static class GraphAnalyzerBuilder {

        private final Map<String, Node> nodes = new LinkedHashMap<>();

        public GraphAnalyzerBuilder withNode(String name, Set<String> descendants) {
            Node node = getNode( name );

            for ( String descendant : descendants ) {
                node.addDescendant( getNode( descendant ) );
            }

            return this;
        }

        public GraphAnalyzerBuilder withNode(String name, String... descendants) {
            return withNode( name, new LinkedHashSet<>( Arrays.asList( descendants ) ) );
        }

        /**
         * Builds the analyzer and triggers traversal of all nodes for detecting potential cycles and calculates the
         * full list of descendants of each node.
         *
         * @return the analyzer
         */
        public GraphAnalyzer build() {
            GraphAnalyzer graphAnalyzer = new GraphAnalyzer( nodes );
            graphAnalyzer.analyze();
            return graphAnalyzer;
        }

        private Node getNode(String name) {
            Node node = nodes.get( name );

            if ( node == null ) {
                node = new Node( name );
                nodes.put( name, node );
            }

            return node;
        }
    }
}
