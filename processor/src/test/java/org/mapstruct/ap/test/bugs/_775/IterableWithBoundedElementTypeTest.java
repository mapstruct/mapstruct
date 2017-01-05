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
package org.mapstruct.ap.test.bugs._775;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * Verifies:
 * <ul>
 * <li>For target properties of type {@link Iterable}, a forged method can be created.
 * <li>For target properties of type {@code Iterable<? extends Integer>}, a custom mapping method that returns
 * {@code List<Integer>} is chosen as property mapping method.
 * </ul>
 *
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@IssueKey("775")
@WithClasses({
    MapperWithForgedIterableMapping.class,
    MapperWithCustomListMapping.class,
    ListContainer.class,
    IterableContainer.class
})
public class IterableWithBoundedElementTypeTest {

    @Test
    public void createsForgedMethodForIterableLowerBoundInteger() {
        ListContainer source = new ListContainer();

        source.setValues( Arrays.asList( "42", "47" ) );
        IterableContainer result = MapperWithForgedIterableMapping.INSTANCE.toContainerWithIterable( source );

        assertThat( result.getValues() ).contains( Integer.valueOf( 42 ), Integer.valueOf( 47 ) );
    }

    @Test
    public void usesListIntegerMethodForIterableLowerBoundInteger() {
        ListContainer source = new ListContainer();

        source.setValues( Arrays.asList( "42", "47" ) );
        IterableContainer result = MapperWithCustomListMapping.INSTANCE.toContainerWithIterable( source );

        assertThat( result.getValues() ).contains( Integer.valueOf( 66 ), Integer.valueOf( 71 ) );
    }
}
