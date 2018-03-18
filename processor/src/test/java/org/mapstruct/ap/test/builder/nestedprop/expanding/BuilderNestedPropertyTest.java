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
package org.mapstruct.ap.test.builder.nestedprop.expanding;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that nested property mapping works with an immutable intermediate type.
 */
@WithClasses({
    FlattenedStock.class,
    ImmutableExpandedStock.class,
    ImmutableArticle.class,
    ExpandingMapper.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class BuilderNestedPropertyTest {

    @Test
    public void testNestedImmutablePropertyMapper() {
        FlattenedStock stock = new FlattenedStock( "Sock", "Tie", 33 );
        ImmutableExpandedStock expandedTarget = ExpandingMapper.INSTANCE.writeToNestedProperty( stock );
        assertThat( expandedTarget ).isNotNull();
        assertThat( expandedTarget.getCount() ).isEqualTo( 33 );
        assertThat( expandedTarget.getSecond() ).isNull();
        assertThat( expandedTarget.getFirst() ).isNotNull();
        assertThat( expandedTarget.getFirst().getDescription() ).isEqualTo( "Sock" );
    }
}
