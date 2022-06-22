/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.mapstruct.ap.test.callbacks.returning.NodeMapperContext.ContextListener;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

/**
 * Test case for https://github.com/mapstruct/mapstruct/issues/469
 *
 * @author Pascal Grün
 */
@IssueKey( "469" )
@WithClasses( { Attribute.class, AttributeDto.class, Node.class, NodeDto.class, NodeMapperDefault.class,
    NodeMapperWithContext.class, NodeMapperContext.class, Number.class, NumberMapperDefault.class,
    NumberMapperContext.class, NumberMapperWithContext.class } )
public class CallbacksWithReturnValuesTest {
    @ProcessorTest
    public void mappingWithDefaultHandlingRaisesStackOverflowError() {
        Node root = buildNodes();
        assertThatThrownBy( () -> NodeMapperDefault.INSTANCE.nodeToNodeDto( root ) )
            .isInstanceOf( StackOverflowError.class );
    }

    @ProcessorTest
    public void updatingWithDefaultHandlingRaisesStackOverflowError() {
        Node root = buildNodes();
        assertThatThrownBy( () -> NodeMapperDefault.INSTANCE.nodeToNodeDto( root, new NodeDto() ) )
            .isInstanceOf( StackOverflowError.class );
    }

    @ProcessorTest
    public void mappingWithContextCorrectlyResolvesCycles() {
        final AtomicReference<Integer> contextLevel = new AtomicReference<>( null );
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

    @ProcessorTest
    public void numberMappingWithoutContextDoesNotUseCache() {
        Number n1 = NumberMapperDefault.INSTANCE.integerToNumber( 2342 );
        Number n2 = NumberMapperDefault.INSTANCE.integerToNumber( 2342 );
        assertThat( n1 ).isEqualTo( n2 );
        assertThat( n1 ).isNotSameAs( n2 );
    }

    @ProcessorTest
    public void numberMappingWithContextUsesCache() {
        NumberMapperContext.putCache( new Number( 2342 ) );
        Number n1 = NumberMapperWithContext.INSTANCE.integerToNumber( 2342 );
        Number n2 = NumberMapperWithContext.INSTANCE.integerToNumber( 2342 );
        assertThat( n1 ).isEqualTo( n2 );
        assertThat( n1 ).isSameAs( n2 );
        NumberMapperContext.clearCache();
    }

    @ProcessorTest
    public void numberMappingWithContextCallsVisitNumber() {
        Number n1 = NumberMapperWithContext.INSTANCE.integerToNumber( 1234 );
        Number n2 = NumberMapperWithContext.INSTANCE.integerToNumber( 5678 );
        assertThat( NumberMapperContext.getVisited() ).isEqualTo( Arrays.asList( n1, n2 ) );
        NumberMapperContext.clearVisited();
    }
}
