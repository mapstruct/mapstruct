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
package org.mapstruct.ap.test.bugs._1247;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@IssueKey("1247")
@RunWith(AnnotationProcessorTestRunner.class)
@WithClasses({
    Issue1247Mapper.class,
    DtoIn.class,
    DtoOut.class,
    InternalData.class,
    InternalDto.class,
    OtherDtoOut.class,
    OtherInternalData.class,
    OtherInternalDto.class
})
public class Issue1247Test {

    @Test
    public void shouldCorrectlyUseMappings() {

        DtoIn in = new DtoIn( "data", "data2" );
        List<String> list = Arrays.asList( "first", "second" );
        DtoOut out = Issue1247Mapper.INSTANCE.map( in, list );

        assertThat( out ).isNotNull();
        assertThat( out.getData() ).isEqualTo( "data" );
        assertThat( out.getInternal() ).isNotNull();
        assertThat( out.getInternal().getData2() ).isEqualTo( "data2" );
        assertThat( out.getInternal().getInternalData() ).isNotNull();
        assertThat( out.getInternal().getInternalData().getList() ).containsExactly( "first", "second" );
    }

    @Test
    public void shouldCorrectlyUseMappingsWithConstantsExpressionsAndDefaults() {

        DtoIn in = new DtoIn( "data", "data2" );
        List<String> list = Arrays.asList( "first", "second" );
        OtherDtoOut out = Issue1247Mapper.INSTANCE.mapWithConstantExpressionAndDefault( in, list );

        assertThat( out ).isNotNull();
        assertThat( out.getData() ).isEqualTo( "data" );
        assertThat( out.getConstant() ).isEqualTo( "someConstant" );
        assertThat( out.getInternal() ).isNotNull();
        // This will not be mapped by the @Mapping(target = "internal", source = "in") because we have one more
        // symmetric mapping @Mapping(target = "internal.expression", expression = "java(\"testingExpression\")")
        assertThat( out.getInternal().getData2() ).isNull();
        assertThat( out.getInternal().getExpression() ).isEqualTo( "testingExpression" );
        assertThat( out.getInternal().getInternalData() ).isNotNull();
        assertThat( out.getInternal().getInternalData().getList() ).containsExactly( "first", "second" );
        assertThat( out.getInternal().getInternalData().getDefaultValue() ).isEqualTo( "data2" );
    }

    @Test
    public void shouldCorrectlyUseMappingsWithConstantsExpressionsAndUseDefault() {

        DtoIn in = new DtoIn( "data", null );
        List<String> list = Arrays.asList( "first", "second" );
        OtherDtoOut out = Issue1247Mapper.INSTANCE.mapWithConstantExpressionAndDefault( in, list );

        assertThat( out ).isNotNull();
        assertThat( out.getData() ).isEqualTo( "data" );
        assertThat( out.getConstant() ).isEqualTo( "someConstant" );
        assertThat( out.getInternal() ).isNotNull();
        assertThat( out.getInternal().getData2() ).isNull();
        assertThat( out.getInternal().getExpression() ).isEqualTo( "testingExpression" );
        assertThat( out.getInternal().getInternalData() ).isNotNull();
        assertThat( out.getInternal().getInternalData().getList() ).containsExactly( "first", "second" );
        assertThat( out.getInternal().getInternalData().getDefaultValue() ).isEqualTo( "missing" );
    }
}
