package org.mapstruct.ap.test.optionalmapping;

public interface BaseOptionalTestMapper {

    Target toTarget(Source source);

    Source fromTarget(Target target);

}
