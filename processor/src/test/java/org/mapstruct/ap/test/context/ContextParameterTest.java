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
package org.mapstruct.ap.test.context;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.BeforeMapping;
import org.mapstruct.Context;
import org.mapstruct.ObjectFactory;
import org.mapstruct.ap.test.context.Node.Attribute;
import org.mapstruct.ap.test.context.NodeDto.AttributeDto;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Tests the usage of the {@link Context} annotation in the following situations:
 * <ul>
 * <li>passing the parameter to property mapping methods (create and update)
 * <li>passing the parameter to forged iterable methods
 * <li>passing the parameter to forged bean mapping methods
 * <li>passing the parameter to factory methods (with and without {@link ObjectFactory})
 * <li>passing the parameter to lifecycle methods (in this case, {@link BeforeMapping}
 * <li>passing multiple parameters, with varied order of context params and mapping source params
 * <li>calling lifecycle methods on context params
 * </ul>
 *
 * @author Andreas Gudian
 */
@IssueKey("975")
@WithClasses({
    Node.class,
    NodeDto.class,
    NodeMapperWithContext.class,
    AutomappingNodeMapperWithContext.class,
    AutomappingNodeMapperWithSelfContainingContext.class,
    CycleContext.class,
    FactoryContext.class,
    CycleContextLifecycleMethods.class,
    FactoryContextMethods.class,
    SelfContainingCycleContext.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ContextParameterTest {

    private static final int MAGIC_NUMBER_OFFSET = 10;

    @Test
    public void mappingWithContextCorrectlyResolvesCycles() {
        Node root = buildNodes();
        NodeDto rootDto =
            NodeMapperWithContext.INSTANCE.nodeToNodeDto( new FactoryContext( 0, 10 ), root, new CycleContext() );
        assertResult( rootDto );

        NodeDto updated = new NodeDto( 0 );
        NodeMapperWithContext.INSTANCE.nodeToNodeDto( new FactoryContext( 1, 10 ), root, updated, new CycleContext() );
        assertResult( updated );
    }

    @Test
    public void automappingWithContextCorrectlyResolvesCycles() {
        Node root = buildNodes();
        NodeDto rootDto = AutomappingNodeMapperWithContext.INSTANCE
            .nodeToNodeDto( root, new CycleContext(), new FactoryContext( 0, MAGIC_NUMBER_OFFSET ) );
        assertResult( rootDto );

        NodeDto updated = new NodeDto( 0 );
        AutomappingNodeMapperWithContext.INSTANCE
            .nodeToNodeDto( root, updated, new CycleContext(), new FactoryContext( 1, 10 ) );
        assertResult( updated );
    }

    @Test
    public void automappingWithSelfContainingContextCorrectlyResolvesCycles() {
        Node root = buildNodes();
        NodeDto rootDto = AutomappingNodeMapperWithSelfContainingContext.INSTANCE
            .nodeToNodeDto(
                root,
                new SelfContainingCycleContext(),
                new FactoryContext( 0, MAGIC_NUMBER_OFFSET ) );
        assertResult( rootDto );

        NodeDto updated = new NodeDto( 0 );
        AutomappingNodeMapperWithSelfContainingContext.INSTANCE
            .nodeToNodeDto(
                root,
                updated,
                new SelfContainingCycleContext(),
                new FactoryContext( 1, 10 ) );
        assertResult( updated );
    }

    private void assertResult(NodeDto rootDto) {
        assertThat( rootDto ).isNotNull();
        assertThat( rootDto.getId() ).isEqualTo( 0 );

        AttributeDto rootAttribute = rootDto.getAttributes().get( 0 );
        assertThat( rootAttribute.getNode() ).isSameAs( rootDto );
        assertThat( rootAttribute.getMagicNumber() ).isEqualTo( 1 + MAGIC_NUMBER_OFFSET );

        assertThat( rootDto.getChildren() ).hasSize( 1 );

        NodeDto node1 = rootDto.getChildren().get( 0 );
        assertThat( node1.getParent() ).isSameAs( rootDto );
        assertThat( node1.getId() ).isEqualTo( 1 );

        AttributeDto node1Attribute = node1.getAttributes().get( 0 );
        assertThat( node1Attribute.getNode() ).isSameAs( node1 );
        assertThat( node1Attribute.getMagicNumber() ).isEqualTo( 2 + MAGIC_NUMBER_OFFSET );

    }

    private static Node buildNodes() {
        Node root = new Node( "root" );
        root.addAttribute( new Attribute( "name", "root", 1 ) );

        Node node1 = new Node( "node1" );
        node1.addAttribute( new Attribute( "name", "node1", 2 ) );

        root.addChild( node1 );

        return root;
    }
}
