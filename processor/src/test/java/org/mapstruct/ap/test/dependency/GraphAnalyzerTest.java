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
package org.mapstruct.ap.test.dependency;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.mapstruct.ap.internal.model.dependency.GraphAnalyzer;
import org.mapstruct.ap.internal.util.Strings;

/**
 * Unit test for {@link GraphAnalyzer}.
 *
 * @author Gunnar Morling
 */
public class GraphAnalyzerTest {

    @Test
    public void emptyGraph() {
        GraphAnalyzer detector = GraphAnalyzer.builder().build();
        assertThat( detector.getCycles() ).isEmpty();
    }

    @Test
    public void singleNode() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a" ).build();

        assertThat( detector.getCycles() ).isEmpty();
        assertThat( detector.getTraversalSequence( "a" ) ).isEqualTo( 0 );
    }

    @Test
    public void twoNodesWithoutCycle() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b" )
                .withNode( "b" )
                .build();

        assertThat( detector.getCycles() ).isEmpty();
        assertThat( detector.getTraversalSequence( "b" ) ).isEqualTo( 0 );
        assertThat( detector.getTraversalSequence( "a" ) ).isEqualTo( 1 );
    }

    @Test
    public void twoNodesWithCycle() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b" )
                .withNode( "b", "a" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b -> a" );
    }

    @Test
    public void threeNodesWithCycleBetweenTwo() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b" )
                .withNode( "b", "a", "c" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b -> a" );
    }

    @Test
    public void twoNodesWithSharedDescendantWithoutCycle() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b" )
                .withNode( "b", "c" )
                .withNode( "a", "c" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).isEmpty();

        assertThat( detector.getTraversalSequence( "c" ) ).isEqualTo( 0 );
        assertThat( detector.getTraversalSequence( "b" ) ).isEqualTo( 1 );
        assertThat( detector.getTraversalSequence( "a" ) ).isEqualTo( 2 );
    }

    @Test
    public void threeNodesWithoutCycle() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b" )
                .withNode( "c", "b" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).isEmpty();

        assertThat( detector.getTraversalSequence( "b" ) ).isEqualTo( 0 );
        assertThat( detector.getTraversalSequence( "a" ) ).isEqualTo( 1 );
        assertThat( detector.getTraversalSequence( "c" ) ).isEqualTo( 2 );
    }

    @Test
    public void fourNodesWithCycleBetweenThree() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b" )
                .withNode( "b", "c" )
                .withNode( "c", "d" )
                .withNode( "d", "b" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "b -> c -> d -> b" );
    }

    @Test
    public void fourNodesWithTwoCycles() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b" )
                .withNode( "b", "a" )
                .withNode( "c", "d" )
                .withNode( "d", "c" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b -> a", "c -> d -> c" );
    }

    @Test
    public void fourNodesWithoutCycle() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b1" )
                .withNode( "a", "b2" )
                .withNode( "b1", "c" )
                .withNode( "b2", "c" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).isEmpty();

        assertThat( detector.getTraversalSequence( "c" ) ).isEqualTo( 0 );
        assertThat( detector.getTraversalSequence( "b1" ) ).isEqualTo( 1 );
        assertThat( detector.getTraversalSequence( "b2" ) ).isEqualTo( 2 );
        assertThat( detector.getTraversalSequence( "a" ) ).isEqualTo( 3 );
    }

    @Test
    public void fourNodesWithCycle() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b1" )
                .withNode( "a", "b2" )
                .withNode( "b1", "c" )
                .withNode( "b2", "c" )
                .withNode( "c", "a" )
                .build();

        assertThat( asStrings( detector.getCycles() ) ).containsOnly( "a -> b1 -> c -> a" );
        // note: cycle "a -> b2 -> c -> a" is currently not reported, see #856.
    }

    @Test
    public void eightNodesWithoutCycle() {
        GraphAnalyzer detector = GraphAnalyzer.withNode( "a", "b1" )
                .withNode( "a", "b2" )
                .withNode( "b1", "c1" )
                .withNode( "b1", "c2" )
                .withNode( "b2", "c3" )
                .withNode( "b2", "c4" )
                .build();

        assertThat( detector.getCycles() ).isEmpty();

        assertThat( detector.getTraversalSequence( "c1" ) ).isEqualTo( 0 );
        assertThat( detector.getTraversalSequence( "c2" ) ).isEqualTo( 1 );
        assertThat( detector.getTraversalSequence( "b1" ) ).isEqualTo( 2 );
        assertThat( detector.getTraversalSequence( "c3" ) ).isEqualTo( 3 );
        assertThat( detector.getTraversalSequence( "c4" ) ).isEqualTo( 4 );
        assertThat( detector.getTraversalSequence( "b2" ) ).isEqualTo( 5 );
        assertThat( detector.getTraversalSequence( "a" ) ).isEqualTo( 6 );
    }

    private Set<String> asStrings(Set<List<String>> cycles) {
        Set<String> asStrings = new HashSet<String>();

        for ( List<String> cycle : cycles ) {
            asStrings.add( asString( cycle ) );
        }

        return asStrings;
    }

    private String asString(List<String> cycle) {
        return Strings.join( cycle, " -> " );
    }
}
