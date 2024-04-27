package org.mapstruct.ap.test.canonicalconstructor.defaultcomponentmodel;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.ap.test.canonicalconstructor.shared.UserEntity;

public class ExampleMapper {

    private int number = 404;

    public Integer map(Integer entity){
        return number;
    }

}
