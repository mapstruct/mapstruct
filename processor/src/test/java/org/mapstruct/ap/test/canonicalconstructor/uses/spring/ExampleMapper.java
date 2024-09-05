package org.mapstruct.ap.test.canonicalconstructor.uses.spring;

import org.springframework.stereotype.Component;

@Component
public class ExampleMapper {

    private int number = 404;

    public Integer map(Integer entity) {
        return number;
    }

}
