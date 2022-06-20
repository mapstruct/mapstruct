/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2101;

import static org.assertj.core.api.Assertions.assertThat;

import org.mapstruct.testutil.IssueKey;
import org.mapstruct.testutil.ProcessorTest;
import org.mapstruct.testutil.WithClasses;

@IssueKey("2101")
public class Issue2101Test {

    @ProcessorTest
    @WithClasses(Issue2101Mapper.class)
    public void shouldMap() {

        Issue2101Mapper.Source source = new Issue2101Mapper.Source();
        source.value1 = "v1";
        source.value2 = "v2";

        Issue2101Mapper.Target target = Issue2101Mapper.INSTANCE.map( source );

        assertThat( target.codeValue1.code ).isEqualTo( "c1" );
        assertThat( target.codeValue1.value ).isEqualTo( "v1" );
        assertThat( target.codeValue2.code ).isEqualTo( "c2" );
        assertThat( target.codeValue2.value ).isEqualTo( "v2" );

    }

    @ProcessorTest
    @WithClasses(Issue2101AdditionalMapper.class)
    public void shouldMapSomeAdditionalTests1() {
        Issue2101AdditionalMapper.Source source = new Issue2101AdditionalMapper.Source();
        source.value = new Issue2101AdditionalMapper.NestedSource();
        source.value.nestedValue1 = "value1";
        source.value.nestedValue2 = "value2";
        source.valueThrowOffPath = "value3";

        Issue2101AdditionalMapper.Target target = Issue2101AdditionalMapper.INSTANCE.map1( source );
        assertThat( target.value1 ).isEqualTo( "value1" );
        assertThat( target.value2 ).isEqualTo( "value2" );
        assertThat( target.value3 ).isEqualTo( "value3" );
    }

    @ProcessorTest
    @WithClasses(Issue2101AdditionalMapper.class)
    public void shouldMapSomeAdditionalTests2() {
        Issue2101AdditionalMapper.Source source = new Issue2101AdditionalMapper.Source();
        source.value = new Issue2101AdditionalMapper.NestedSource();
        source.value.nestedValue1 = "value1";
        source.value.nestedValue2 = "value2";
        source.valueThrowOffPath = "value3";

        Issue2101AdditionalMapper.Target target = Issue2101AdditionalMapper.INSTANCE.map2( source );
        assertThat( target.value1 ).isEqualTo( "value1" );
        assertThat( target.value2 ).isEqualTo( "value1" );
        assertThat( target.value3 ).isEqualTo( "test" );

    }

    @ProcessorTest
    @WithClasses(Issue2102IgnoreAllButMapper.class)
    public void shouldApplyIgnoreAllButTemplateOfMethod1() {

        Issue2102IgnoreAllButMapper.Source source = new Issue2102IgnoreAllButMapper.Source();
        source.value1 = "value1";
        source.value2 = "value2";

        Issue2102IgnoreAllButMapper.Target target = Issue2102IgnoreAllButMapper.INSTANCE.map1( source );
        assertThat( target.value1 ).isEqualTo( "value1" );

        target = Issue2102IgnoreAllButMapper.INSTANCE.map2( source );
        assertThat( target.value1 ).isEqualTo( "value2" );

    }
}
