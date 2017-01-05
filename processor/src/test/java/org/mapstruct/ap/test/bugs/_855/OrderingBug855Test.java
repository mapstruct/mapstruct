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
package org.mapstruct.ap.test.bugs._855;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Andreas Gudian
 *
 */
@WithClasses({ OrderedSource.class, OrderedTarget.class, OrderDemoMapper.class })
@RunWith(AnnotationProcessorTestRunner.class)
public class OrderingBug855Test {

    @Test
    @IssueKey("855")
    public void shouldApplyCorrectOrderingWithDependsOn() {
        OrderedSource source = new OrderedSource();

        OrderedTarget target = OrderDemoMapper.INSTANCE.orderedWithDependsOn( source );

        assertThat( target.getOrder() ).containsExactly( "field2", "field0", "field1", "field3", "field4" );
    }

    @Test
    public void shouldRetainDefaultOrderWithoutDependsOn() {
        OrderedSource source = new OrderedSource();

        OrderedTarget target = OrderDemoMapper.INSTANCE.orderedWithoutDependsOn( source );

        assertThat( target.getOrder() ).containsExactly( "field0", "field1", "field2", "field3", "field4" );
    }
}
