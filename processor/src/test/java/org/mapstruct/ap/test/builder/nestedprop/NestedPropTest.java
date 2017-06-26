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
package org.mapstruct.ap.test.builder.nestedprop;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mapstruct.ap.test.builder.BuilderTests;
import org.mapstruct.ap.testutil.WithClasses;

@WithClasses( {FlattenedSource.class, ExpandedTarget.class, ImmutableTargetContainer.class} )
@Category(BuilderTests.class)
public class NestedPropTest {
    @Test
    @WithClasses( {FlattenedMapper.class} )
    public void testNestedPropHappyPath() {

    }
}
