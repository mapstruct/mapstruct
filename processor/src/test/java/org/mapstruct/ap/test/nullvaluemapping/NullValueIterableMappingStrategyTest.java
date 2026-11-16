/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.nullvaluemapping;

import org.mapstruct.ap.test.nullvaluemapping._target.CarDto;
import org.mapstruct.ap.test.nullvaluemapping.source.Car;
import org.mapstruct.ap.testutil.IssueKey;
import org.mapstruct.ap.testutil.ProcessorTest;
import org.mapstruct.ap.testutil.WithClasses;
import org.mapstruct.ap.testutil.compilation.annotation.ProcessorOption;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Filip Hrisafov
 */
@WithClasses({
    CarDto.class,
    Car.class
})
@IssueKey("2953")
public class NullValueIterableMappingStrategyTest {

    @ProcessorTest
    @ProcessorOption(name = "mapstruct.nullValueIterableMappingStrategy", value = "return_default")
    @WithClasses({
        CarListMapper.class
    })
    void globalNullIterableMappingStrategy() {
        assertThat( CarListMapper.INSTANCE.carsToCarDtoList( null ) ).isEmpty();
    }

    @ProcessorTest
    @ProcessorOption(name = "mapstruct.nullValueIterableMappingStrategy", value = "return_default")
    @WithClasses({
        CarListMapperSettingOnMapper.class
    })
    void globalNullMapMappingStrategyWithOverrideInMapper() {
        // Explicit definition in @Mapper should override global
        assertThat( CarListMapperSettingOnMapper.INSTANCE.carsToCarDtoList( null ) ).isNull();
    }
}
