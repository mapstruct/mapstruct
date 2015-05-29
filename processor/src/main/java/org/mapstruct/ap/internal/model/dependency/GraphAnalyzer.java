/**
 *  Copyright 2012-2015 Gunnar Morling (http://www.gunnarmorling.de/)
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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

    private GraphAnalyzer(Map<String, Node> nodes) {
        this.nodes = nodes;
        cycles = new HashSet<List<String>>();
        currentPath = new Stack<Node>();
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
     * Returns all the descendants of the given node, either direct or transitive ones.
     * <p>
     * <b>Note</b>:The list will only be complete if the graph contains no cycles.
     *
     * @param name the node name to get the descendants for
     * @return the descendants
     */
    public Set<String> getAllDescendants(String name) {
        Node node = nodes.get( name );
        return node != null ? node.getAllDescendants() : Collections.<String>emptySet();
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
            node.getAllDescendants().addAll( descendant.getAllDescendants() );
        }

        node.setProcessed( true );
        currentPath.pop();
    }

    private List<String> getCurrentCycle(Node start) {
        List<String> cycle = new ArrayList<String>();
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

        private final Map<String, Node> nodes = new HashMap<String, Node>();

        public GraphAnalyzerBuilder withNode(String name, List<String> descendants) {
            Node node = getNode( name );

            for ( String descendant : descendants ) {
                node.addDescendant( getNode( descendant ) );
            }

            return this;
        }

        public GraphAnalyzerBuilder withNode(String name, String... descendants) {
            return withNode( name, Arrays.asList( descendants ) );
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
