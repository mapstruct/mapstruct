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
package org.mapstruct.ap.test.builder.lifecycle;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@RunWith( AnnotationProcessorTestRunner.class )
@IssueKey( "1433" )
@WithClasses( {
    Item.class,
    ItemDto.class,
    MappingContext.class,
    Order.class,
    OrderDto.class,
    OrderMapper.class
} )
public class BuilderLifecycleCallbacksTest {

    @Test
    public void lifecycleMethodsShouldBeInvoked() {
        OrderDto source = new OrderDto();
        source.setCreator( "Filip" );
        ItemDto item1 = new ItemDto();
        item1.setName( "Laptop" );
        ItemDto item2 = new ItemDto();
        item2.setName( "Keyboard" );
        source.setItems( Arrays.asList( item1, item2 ) );
        MappingContext context = new MappingContext();

        OrderMapper.INSTANCE.map( source, context );

        assertThat( context.getInvokedMethods() )
            .contains(
                "beforeWithoutParameters",
                "beforeWithBuilderTargetType",
                "beforeWithBuilderTarget",
                "afterWithoutParameters",
                "afterWithBuilderTargetType",
                "afterWithBuilderTarget",
                "afterWithBuilderTargetReturningTarget"
            );
    }
}
