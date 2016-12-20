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
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Tests the usage of the {@link Context} annotation in the following situations:
 * <ul>
 * <li>passing the parameter to property mapping methods
 * <li>passing the parameter to forged iterable methods
 * <li>passing the parameter to forged bean mapping methods
 * <li>passing the parameter to {@link ObjectFactory} methods
 * <li>passing the parameter to lifecycle methods (in this case, {@link BeforeMapping}
 * <li>passing multiple parameters, with varied order of context params and mapping source params
 * </ul>
 *
 * @author Andreas Gudian
 */
@IssueKey("975")
@WithClasses({
    Node.class,
    NodeDTO.class,
    NodeMapperWithContext.class,
    AutomappingNodeMapperWithContext.class,
    CycleContext.class,
    FactoryContext.class,
    CycleContextLifecycleMethods.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class ContextParameterTest {

    @Test
    public void mappingWithContextCorrectlyResolvesCycles() {
        Node root = buildNodes();
        NodeDTO rootDTO =
            NodeMapperWithContext.INSTANCE.nodeToNodeDTO( new FactoryContext( 0 ), root, new CycleContext() );
        assertResult( rootDTO );

        NodeDTO updated = new NodeDTO( 0 );
        NodeMapperWithContext.INSTANCE.nodeToNodeDTO( new FactoryContext( 1 ), root, updated, new CycleContext() );
        assertResult( updated );
    }

    @Test
    public void automappingWithContextCorrectlyResolvesCycles() {
        Node root = buildNodes();
        NodeDTO rootDTO = AutomappingNodeMapperWithContext.INSTANCE
            .nodeToNodeDTO( root, new CycleContext(), new FactoryContext( 0 ) );
        assertResult( rootDTO );

        NodeDTO updated = new NodeDTO( 0 );
        AutomappingNodeMapperWithContext.INSTANCE
            .nodeToNodeDTO( root, updated, new CycleContext(), new FactoryContext( 1 ) );
        assertResult( updated );
    }

    private void assertResult(NodeDTO rootDTO) {
        assertThat( rootDTO ).isNotNull();
        assertThat( rootDTO.getId() ).isEqualTo( 0 );

        assertThat( rootDTO.getChildren() ).hasSize( 1 );
        assertThat( rootDTO.getChildren().get( 0 ).getParent() ).isSameAs( rootDTO );
        assertThat( rootDTO.getChildren().get( 0 ).getId() ).isEqualTo( 1 );
        assertThat( rootDTO.getAttributes().get( 0 ).getNode() ).isSameAs( rootDTO );
    }

    private static Node buildNodes() {
        Node root = new Node( "root" );
        root.addAttribute( new Attribute( "name", "root" ) );

        Node node1 = new Node( "node1" );
        node1.addAttribute( new Attribute( "name", "node1" ) );

        root.addChild( node1 );

        return root;
    }
}
