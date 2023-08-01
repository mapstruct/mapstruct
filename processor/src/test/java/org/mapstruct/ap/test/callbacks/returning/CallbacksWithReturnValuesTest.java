/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.callbacks.returning;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.AfterEach;
import org.mapstruct.ap.test.callbacks.returning.NodeMapperContext.ContextListener;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Test case for https://github.com/mapstruct/mapstruct/issues/469
 *
 * @author Pascal Grün
 */
@IssueKey( "469" )
@WithClasses( { Attribute.class, AttributeDto.class, Node.class, NodeDto.class, NodeMapperDefault.class,
    NodeMapperWithContext.class, NodeMapperContext.class, Number.class, NumberMapperDefault.class,
    NumberMapperContext.class, NumberMapperWithContext.class } )
class CallbacksWithReturnValuesTest {
    @AfterEach
    void cleanup() {
        NumberMapperContext.clearCache();
        NumberMapperContext.clearVisited();
    }

    @ProcessorTest
    void mappingWithDefaultHandlingRaisesStackOverflowError() {
        Node root = buildNodes();
        assertThatThrownBy( () -> NodeMapperDefault.INSTANCE.nodeToNodeDto( root ) )
            .isInstanceOf( StackOverflowError.class );
    }

    @ProcessorTest
    void updatingWithDefaultHandlingRaisesStackOverflowError() {
        Node root = buildNodes();
        assertThatThrownBy( () -> NodeMapperDefault.INSTANCE.nodeToNodeDto( root, new NodeDto() ) )
            .isInstanceOf( StackOverflowError.class );
    }

    @ProcessorTest
    void mappingWithContextCorrectlyResolvesCycles() {
        final AtomicReference<Integer> contextLevel = new AtomicReference<>( null );
        ContextListener contextListener = (level, method, source, target) -> contextLevel.set( level );

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
    void numberMappingWithoutContextDoesNotUseCache() {
        Number n1 = NumberMapperDefault.INSTANCE.integerToNumber( 2342 );
        Number n2 = NumberMapperDefault.INSTANCE.integerToNumber( 2342 );

        assertThat( n1 ).isEqualTo( n2 );
        assertThat( n1 ).isNotSameAs( n2 );
    }

    @ProcessorTest
    void numberMappingWithContextUsesCache() {
        NumberMapperContext.putCache( new Number( 2342 ) );
        Number n1 = NumberMapperWithContext.INSTANCE.integerToNumber( 2342 );
        Number n2 = NumberMapperWithContext.INSTANCE.integerToNumber( 2342 );

        assertThat( n1 ).isEqualTo( n2 );
        assertThat( n1 ).isSameAs( n2 );
    }

    @ProcessorTest
    void numberMappingWithContextCallsVisitNumber() {
        Number n1 = NumberMapperWithContext.INSTANCE.integerToNumber( 1234 );
        Number n2 = NumberMapperWithContext.INSTANCE.integerToNumber( 5678 );

        assertThat( NumberMapperContext.getVisited() ).isEqualTo( Arrays.asList( n1, n2 ) );
    }

    @ProcessorTest
    @IssueKey( "2955" )
    void numberUpdateMappingWithContextUsesCacheAndThereforeDoesNotVisitNumber() {
        Number target = new Number();
        Number expectedReturn = new Number( 2342 );
        NumberMapperContext.putCache( expectedReturn );
        NumberMapperWithContext.INSTANCE.integerToNumber( 2342, target );

        assertThat( NumberMapperContext.getVisited() ).isEmpty();
    }

    @ProcessorTest
    @IssueKey( "2955" )
    void numberUpdateMappingWithContextCallsVisitNumber() {
        Number target = new Number();
        NumberMapperWithContext.INSTANCE.integerToNumber( 2342, target );

        assertThat( NumberMapperContext.getVisited() ).contains( target );
    }
}
