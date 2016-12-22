/**
 *  Copyright 2012-2016 Gunnar Morling (http://www.gunnarmorling.de/)
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
    CycleContext.class,
    FactoryContext.class,
    CycleContextLifecycleMethods.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ContextParameterTest {

    private static final int MATIC_NUMBER_OFFSET = 10;

    @Test
    public void mappingWithContextCorrectlyResolvesCycles() {
        Node root = buildNodes();
        NodeDto rootDTO =
            NodeMapperWithContext.INSTANCE.nodeToNodeDTO( new FactoryContext( 0, 10 ), root, new CycleContext() );
        assertResult( rootDTO );

        NodeDto updated = new NodeDto( 0 );
        NodeMapperWithContext.INSTANCE.nodeToNodeDTO( new FactoryContext( 1, 10 ), root, updated, new CycleContext() );
        assertResult( updated );
    }

    @Test
    public void automappingWithContextCorrectlyResolvesCycles() {
        Node root = buildNodes();
        NodeDto rootDTO = AutomappingNodeMapperWithContext.INSTANCE
            .nodeToNodeDTO( root, new CycleContext(), new FactoryContext( 0, MATIC_NUMBER_OFFSET ) );
        assertResult( rootDTO );

        NodeDto updated = new NodeDto( 0 );
        AutomappingNodeMapperWithContext.INSTANCE
            .nodeToNodeDTO( root, updated, new CycleContext(), new FactoryContext( 1, 10 ) );
        assertResult( updated );
    }

    private void assertResult(NodeDto rootDTO) {
        assertThat( rootDTO ).isNotNull();
        assertThat( rootDTO.getId() ).isEqualTo( 0 );

        AttributeDto rootAttribute = rootDTO.getAttributes().get( 0 );
        assertThat( rootAttribute.getNode() ).isSameAs( rootDTO );
        assertThat( rootAttribute.getMagicNumber() ).isEqualTo( 1 + MATIC_NUMBER_OFFSET );

        assertThat( rootDTO.getChildren() ).hasSize( 1 );

        NodeDto node1 = rootDTO.getChildren().get( 0 );
        assertThat( node1.getParent() ).isSameAs( rootDTO );
        assertThat( node1.getId() ).isEqualTo( 1 );

        AttributeDto node1Attribute = node1.getAttributes().get( 0 );
        assertThat( node1Attribute.getNode() ).isSameAs( node1 );
        assertThat( node1Attribute.getMagicNumber() ).isEqualTo( 2 + MATIC_NUMBER_OFFSET );

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
