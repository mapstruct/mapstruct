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
package org.mapstruct.ap.test.callbacks.returning;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.callbacks.returning.NodeMapperContext.ContextListener;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Test case for https://github.com/mapstruct/mapstruct/issues/469
 *
 * @author Pascal Grün
 */
@IssueKey( "469" )
@WithClasses( { Attribute.class, AttributeDto.class, Node.class, NodeDto.class, NodeMapperDefault.class,
    NodeMapperWithContext.class, NodeMapperContext.class, Number.class, NumberMapperDefault.class,
    NumberMapperContext.class, NumberMapperWithContext.class } )
@RunWith( AnnotationProcessorTestRunner.class )
public class CallbacksWithReturnValuesTest {
    @Test( expected = StackOverflowError.class )
    public void mappingWithDefaultHandlingRaisesStackOverflowError() {
        Node root = buildNodes();
        NodeMapperDefault.INSTANCE.nodeToNodeDto( root );
    }

    @Test( expected = StackOverflowError.class )
    public void updatingWithDefaultHandlingRaisesStackOverflowError() {
        Node root = buildNodes();
        NodeMapperDefault.INSTANCE.nodeToNodeDto( root, new NodeDto() );
    }

    @Test
    public void mappingWithContextCorrectlyResolvesCycles() {
        final AtomicReference<Integer> contextLevel = new AtomicReference<Integer>( null );
        ContextListener contextListener = new ContextListener() {
            @Override
            public void methodCalled(Integer level, String method, Object source, Object target) {
                contextLevel.set( level );
            }
        };

        NodeMapperContext.addContextListener( contextListener );
        try {
            Node root = buildNodes();
            NodeDto rootDto = NodeMapperWithContext.INSTANCE.nodeToNodeDto( root );
            assertThat( rootDto ).isNotNull();
            assertThat( contextLevel.get() ).isEqualTo( Integer.valueOf( 1 ) );
        }
        finally {
            NodeMapperContext.removeContextListener( contextListener );
        }
    }

    private static Node buildNodes() {
        Node root = new Node( "root" );
        root.addAttribute( new Attribute( "name", "root" ) );

        Node node1 = new Node( "node1" );
        node1.addAttribute( new Attribute( "name", "node1" ) );

        root.addChild( node1 );

        return root;
    }

    @Test
    public void numberMappingWithoutContextDoesNotUseCache() {
        Number n1 = NumberMapperDefault.INSTANCE.integerToNumber( 2342 );
        Number n2 = NumberMapperDefault.INSTANCE.integerToNumber( 2342 );
        assertThat( n1 ).isEqualTo( n2 );
        assertThat( n1 ).isNotSameAs( n2 );
    }

    @Test
    public void numberMappingWithContextUsesCache() {
        NumberMapperContext.putCache( new Number( 2342 ) );
        Number n1 = NumberMapperWithContext.INSTANCE.integerToNumber( 2342 );
        Number n2 = NumberMapperWithContext.INSTANCE.integerToNumber( 2342 );
        assertThat( n1 ).isEqualTo( n2 );
        assertThat( n1 ).isSameAs( n2 );
        NumberMapperContext.clearCache();
    }

    @Test
    public void numberMappingWithContextCallsVisitNumber() {
        Number n1 = NumberMapperWithContext.INSTANCE.integerToNumber( 1234 );
        Number n2 = NumberMapperWithContext.INSTANCE.integerToNumber( 5678 );
        assertThat( NumberMapperContext.getVisited() ).isEqualTo( Arrays.asList( n1, n2 ) );
        NumberMapperContext.clearVisited();
    }
}
