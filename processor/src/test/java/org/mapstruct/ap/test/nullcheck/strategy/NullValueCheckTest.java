/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullcheck.strategy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.runner.AnnotationProcessorTestRunner;

import static org.assertj.core.api.Assertions.assertThat;

@IssueKey("1571")
@WithClasses({
    HouseDto.class,
    HouseEntity.class,
    HouseMapper.class,
    HouseMapperConfig.class,
    HouseMapperWithConfig.class
})
@RunWith(AnnotationProcessorTestRunner.class)
public class NullValueCheckTest {

    @Test
    public void testDefinedOnMapper() {

        HouseEntity entity = HouseMapper.INSTANCE.mapWithNvcsOnMapper( new HouseDto() );

        assertThat( entity ).isNotNull();
        assertThat( entity.ownerSet() ).isFalse();
        assertThat( entity.numberSet() ).isFalse();

    }

    @Test
    public void testDefinedOnBean() {

        HouseEntity entity = HouseMapper.INSTANCE.mapWithNvcsOnBean( new HouseDto() );

        assertThat( entity ).isNotNull();
        assertThat( entity.ownerSet() ).isTrue();
        assertThat( entity.numberSet() ).isTrue();

    }

    @Test
    public void testDefinedOnMapping() {

        HouseEntity entity = HouseMapper.INSTANCE.mapWithNvcsOnMapping( new HouseDto() );

        assertThat( entity ).isNotNull();
        assertThat( entity.ownerSet() ).isTrue();
        assertThat( entity.numberSet() ).isFalse();

    }

    @Test
    public void testDefinedOnConfig() {

        HouseEntity entity = HouseMapperWithConfig.INSTANCE.mapWithNvcsOnMapper( new HouseDto() );

        assertThat( entity ).isNotNull();
        assertThat( entity.ownerSet() ).isFalse();
        assertThat( entity.numberSet() ).isFalse();

    }

    @Test
    public void testDefinedOnConfigAndBean() {

        HouseEntity entity = HouseMapperWithConfig.INSTANCE.mapWithNvcsOnBean( new HouseDto() );

        assertThat( entity ).isNotNull();
        assertThat( entity.ownerSet() ).isTrue();
        assertThat( entity.numberSet() ).isTrue();

    }

    @Test
    public void testDefinedOnConfigAndMapping() {

        HouseEntity entity = HouseMapperWithConfig.INSTANCE.mapWithNvcsOnMapping( new HouseDto() );

        assertThat( entity ).isNotNull();
        assertThat( entity.ownerSet() ).isTrue();
        assertThat( entity.numberSet() ).isFalse();

    }

}
