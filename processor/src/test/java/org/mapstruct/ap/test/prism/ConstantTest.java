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
package org.mapstruct.ap.test.prism;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.mapstruct.MappingConstants;
import org.mapstruct.ap.internal.prism.MappingConstantsPrism;

/**
 * Test constants values
 *
 * @author Sjaak Derksen
 */
public class ConstantTest {

    @Test
    public void constantsShouldBeEqual() {
        assertThat( MappingConstants.ANY_REMAINING ).isEqualTo( MappingConstantsPrism.ANY_REMAINING );
        assertThat( MappingConstants.ANY_UNMAPPED ).isEqualTo( MappingConstantsPrism.ANY_UNMAPPED );
        assertThat( MappingConstants.NULL ).isEqualTo( MappingConstantsPrism.NULL );
    }
}
