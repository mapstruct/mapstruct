/*
 * Copyright MapStruct Authors.
 *
 * Licensed under the Apache License version 2.0, available at http://www.apache.org/licenses/LICENSE-2.0
 */
package org.mapstruct.ap.test.bugs._2213;

import java.util.Arrays;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-10-03T10:12:15+0200",
    comments = "version: , compiler: javac, environment: Java 11.0.4 (AdoptOpenJDK)"
)
public class CarMapperImpl implements CarMapper {

    @Override
    public Car toCar(Car2 car2) {
        if ( car2 == null ) {
            return null;
        }

        Car car = new Car();

        int[] intData = car2.getIntData();
        if ( intData != null ) {
            car.setIntData( Arrays.copyOf( intData, intData.length ) );
        }
        Long[] longData = car2.getLongData();
        if ( longData != null ) {
            car.setLongData( Arrays.copyOf( longData, longData.length ) );
        }

        return car;
    }
}
