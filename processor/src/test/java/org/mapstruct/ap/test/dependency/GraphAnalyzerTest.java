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
package org.mapstruct.ap.test.dependency;

import static org.fest.assertions.Assertions.assertThat;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mapstruct.ap.model.dependency.GraphAnalyzer;
import org.mapstruct.ap.util.Strings;

/**
 * Unit test for {@link GraphAnalyzer}.
 *
 * @author Gunnar Morling
 */
public class GraphAnalyzerTest {

    private GraphAnalyzer detector;

    @Before
    public void setUpDetector() {
        detector = new GraphAnalyzer();
    }

    @Test
    public void emptyGraph() {
        detector.analyze();

        assertThat( detector.getCycles() ).isEmpty();
    }

    @Test
    public void singleNode() {
        detector.addNode( "a" );
        detector.analyze();

        assertThat( detector.getCycles() ).isEmpty();
        assertThat( detector.getAllDescendants( "a" ) ).isEmpty();
    }

    @Test
    public void twoNodesWithoutCycle() {
        detector.addNode( "a", "b" );
        detector.addNode( "b" );

        detector.analyze();

        assertThat( detector.getCycles() ).isEmpty();
        assertThat( detector.getAllDescendants( "a" ) ).containsOnly( "b" );
        assertThat( detector.getAllDescendants( "b" ) ).isEmpty();
    }

    @Test
    public void twoNodesWithCycle() {
        detector.addNode( "a", "b" );
        detector.addNode( "b", "a" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b -> a" );
    }

    @Test
    public void threeNodesWithCycleBetweenTwo() {
        detector.addNode( "a", "b" );
        detector.addNode( "b", "a", "c" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b -> a" );
    }

    @Test
    public void twoNodesWithSharedDescendantWithoutCycle() {
        detector.addNode( "a", "b" );
        detector.addNode( "b", "c" );
        detector.addNode( "a", "c" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).isEmpty();
        assertThat( detector.getAllDescendants( "a" ) ).containsOnly( "b", "c" );
        assertThat( detector.getAllDescendants( "b" ) ).containsOnly( "c" );
        assertThat( detector.getAllDescendants( "c" ) ).isEmpty();
    }

    @Test
    public void threeNodesWithoutCycle() {
        detector.addNode( "a", "b" );
        detector.addNode( "c", "b" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).isEmpty();

        assertThat( detector.getAllDescendants( "a" ) ).containsOnly( "b" );
        assertThat( detector.getAllDescendants( "b" ) ).isEmpty();
        assertThat( detector.getAllDescendants( "c" ) ).containsOnly( "b" );
    }

    @Test
    public void fourNodesWithCycleBetweenThree() {
        detector.addNode( "a", "b" );
        detector.addNode( "b", "c" );
        detector.addNode( "c", "d" );
        detector.addNode( "d", "b" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "b -> c -> d -> b" );
    }

    @Test
    public void fourNodesWithTwoCycles() {
        detector.addNode( "a", "b" );
        detector.addNode( "b", "a" );
        detector.addNode( "c", "d" );
        detector.addNode( "d", "c" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b -> a", "c -> d -> c" );
    }

    @Test
    public void fourNodesWithoutCycle() {
        detector.addNode( "a", "b1" );
        detector.addNode( "a", "b2" );
        detector.addNode( "b1", "c" );
        detector.addNode( "b2", "c" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).isEmpty();
        assertThat( detector.getAllDescendants( "a" ) ).containsOnly( "b1", "b2", "c" );
        assertThat( detector.getAllDescendants( "b1" ) ).containsOnly( "c" );
        assertThat( detector.getAllDescendants( "b2" ) ).containsOnly( "c" );
        assertThat( detector.getAllDescendants( "c" ) ).isEmpty();
    }

    @Test
    public void fourNodesWithCycle() {
        detector.addNode( "a", "b1" );
        detector.addNode( "a", "b2" );
        detector.addNode( "b1", "c" );
        detector.addNode( "b2", "c" );
        detector.addNode( "c", "a" );

        detector.analyze();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b1 -> c -> a", "a -> b2 -> c -> a" );
    }

    @Test
    public void eightNodesWithoutCycle() {
        detector.addNode( "a", "b1" );
        detector.addNode( "a", "b2" );
        detector.addNode( "b1", "c1" );
        detector.addNode( "b1", "c2" );
        detector.addNode( "b2", "c3" );
        detector.addNode( "b2", "c4" );

        detector.analyze();

        assertThat( detector.getCycles() ).isEmpty();

        assertThat( detector.getAllDescendants( "a" ) ).containsOnly( "b1", "b2", "c1", "c2", "c3", "c4" );
        assertThat( detector.getAllDescendants( "b1" ) ).containsOnly( "c1", "c2" );
        assertThat( detector.getAllDescendants( "b2" ) ).containsOnly( "c3", "c4" );
    }

    private Set<String> asStrings(Set<List<String>> cycles) {
        Set<String> asStrings = new HashSet<String>();

        for ( List<String> cycle : cycles ) {
            asStrings.add( asString( cycle ) );
        }

        return asStrings;
    }

    private String asString(List<String> cycle) {
        return Strings.join( normalize( cycle ), " -> " );
    }

    /**
     * "Normalizes" a cycle so that the minimum element comes first. E.g. both the cycles {@code b -> c -> a -> b} and
     * {@code c -> a -> b -> c} would be normalized to {@code a -> b -> c -> a}.
     */
    private List<String> normalize(List<String> cycle) {
        // remove the first element
        cycle = cycle.subList( 1, cycle.size() );

        // rotate the cycle so the minimum element comes first
        Collections.rotate( cycle, -cycle.indexOf( Collections.min( cycle ) ) );

        // add the first element add the end to re-close the cycle
        cycle.add( cycle.get( 0 ) );

        return cycle;
    }
}
