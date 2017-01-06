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
package org.mapstruct.ap.test.java8stream.defaultimplementation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

/**
 * @author Filip Hrisfaov
 *
 */
@WithClasses({ NoSetterMapper.class, NoSetterSource.class, NoSetterTarget.class })
@IssueKey("962")
@RunWith(AnnotationProcessorTestRunner.class)
public class NoSetterStreamMappingTest {

    @Test
    public void compilesAndMapsCorrectly() {
        NoSetterSource source = new NoSetterSource();
        source.setListValues( Arrays.asList( "foo", "bar" ).stream() );

        NoSetterTarget target = NoSetterMapper.INSTANCE.toTarget( source );

        assertThat( target.getListValues() ).containsExactly( "foo", "bar" );

        // now test existing instances

        NoSetterSource source2 = new NoSetterSource();
        source2.setListValues( Arrays.asList( "baz" ).stream() );
        List<String> originalCollectionInstance = target.getListValues();

        NoSetterTarget target2 = NoSetterMapper.INSTANCE.toTargetWithExistingTarget( source2, target );

        assertThat( target2.getListValues() ).isSameAs( originalCollectionInstance );
        assertThat( target2.getListValues() ).containsExactly( "baz" );
    }
}
