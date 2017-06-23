package org.mapstruct.ap.test.builder.genericBuilder;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.ERROR)
public abstract class ThingOneMapper {

    abstract ThingOne fromThingTwo(ThingTwo two);


}
