/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
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
