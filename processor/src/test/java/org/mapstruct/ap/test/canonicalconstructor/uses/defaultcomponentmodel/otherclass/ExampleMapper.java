package org.mapstruct.ap.test.canonicalconstructor.uses.defaultcomponentmodel.otherclass;

public class ExampleMapper {

    private int number = 404;

    public Integer map(Integer entity) {
        return number;
    }

}
