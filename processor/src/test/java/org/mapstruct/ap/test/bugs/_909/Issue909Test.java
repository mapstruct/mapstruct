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
package org.mapstruct.ap.test.bugs._909;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.test.bugs._909.ValuesMapper.ValuesHolder;
import org.mapstruct.ap.test.bugs._909.ValuesMapper.ValuesHolderDto;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;
import org.mapstruct.factory.Mappers;

/**
 * Verifies that forged iterable mapping methods for multi-dimensional arrays are generated properly.
 *
 * @author Andreas Gudian
 */
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses(ValuesMapper.class)
public class Issue909Test {
    @Test
    public void properlyCreatesMapperWithValuesAsParameterName() {
        ValuesHolder valuesHolder = new ValuesHolder();
        valuesHolder.setValues( "values" );

        ValuesHolderDto dto = Mappers.getMapper( ValuesMapper.class ).convert( valuesHolder );
        assertThat( dto.getValues() ).isEqualTo( "values" );
    }
}
