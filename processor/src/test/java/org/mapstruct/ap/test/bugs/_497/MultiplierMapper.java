package org.mapstruct.ap.test.bugs._497;

import org.joda.time.DateTime;
import org.mapstruct.Mapper;

import java.util.Random;

@Mapper
public class MultiplierMapper {
    @DoubleMultiplier
    Integer doubleMultiplier(SourceType type) {
        return 2;
    }


    @RandomMultiplier
    Integer randomMultiplier(SourceType type) {
        return new Random(DateTime.now().getMillis()).nextInt(10);
    }

}
