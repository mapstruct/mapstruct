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
package org.mapstruct.ap.test.bugs._895;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithArrayOfByteArray;
import org.mapstruct.ap.test.bugs._895.MultiArrayMapper.WithListOfByteArray;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * Verifies that forged iterable mapping methods for multi-dimensional arrays are generated properly.
 *
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(MultiArrayMapper.class)
public class Issue895Test {
    @Test
    public void properlyMapsMultiDimensionalArrays() {
        WithArrayOfByteArray arrayOfByteArray = new WithArrayOfByteArray();
        arrayOfByteArray.setBytes( new byte[][] { new byte[] { 0, 1 }, new byte[] { 1, 2 } } );

        WithListOfByteArray listOfByteArray = Mappers.getMapper( MultiArrayMapper.class ).convert( arrayOfByteArray );
        assertThat( listOfByteArray.getBytes() ).containsExactly( new byte[] { 0, 1 }, new byte[] { 1, 2 } );

        arrayOfByteArray = Mappers.getMapper( MultiArrayMapper.class ).convert( listOfByteArray );
        assertThat( arrayOfByteArray.getBytes() ).containsExactly( new byte[] { 0, 1 }, new byte[] { 1, 2 } );
    }
}
